package com.isat46.isaback.aspect;

import com.isat46.isaback.dto.reservation.ReservationDto;
import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.model.VerificationToken;
import com.isat46.isaback.repository.VerificationTokenRepository;
import com.isat46.isaback.service.ReservationItemService;
import com.isat46.isaback.util.FileSystemUtils;
import com.isat46.isaback.util.QRCodeUtils;
import com.isat46.isaback.util.ReservationUtils;
import com.isat46.isaback.util.VerificationTokenUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataSource;
import java.nio.file.FileSystems;

@Component
@Aspect
public class EmailAspect {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private ReservationItemService reservationItemService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailAspect.class);

    private final String url = "http://localhost:4200/verify/";

    @Async
    @AfterReturning(pointcut = "execution(* com.isat46.isaback.service.AuthenticationService.registerUser(..))", returning = "result")
    public void sendValidationEmail(JoinPoint joinPoint, Object result){
        UserDto user = ((UserDto) result);
        LOGGER.info("Sending validation email to " + user.getEmail());
        VerificationToken verificationToken = verificationTokenRepository.findOneByEmailOrderByExpirationDateDesc(user.getEmail());
        String verificationUrl = this.url + verificationToken.getToken();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "utf-8");
        try {
            message.setContent(String.format(VerificationTokenUtils.emailTemplate, user.getFirstName(), user.getLastName(), verificationUrl), "text/html");
            messageHelper.setTo(user.getEmail());
            messageHelper.setFrom("${spring.mail.username}");
            messageHelper.setSubject("Verify your email address");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    @AfterReturning(pointcut = "execution(* com.isat46.isaback.service.ReservationService.updateReservation(..))", returning = "result")
    public void sendReservationConfirmationEmail(JoinPoint joinPoint, Object result){
        ReservationDto reservationDto = ((ReservationDto) result);
        UserDto employee = reservationDto.getEmployee();
        /*
         *  SET EMAIL TO YOUR EMAIL WHEN DEBUGING
         */
        String emailTo = employee.getEmail();

        LOGGER.info("Sending reservation confirmation email to " + emailTo);

        try {
            QRCodeUtils.generateReservationQRCodeImage(ReservationUtils.getReservationInformation(reservationDto, reservationItemService.findReservationItemsByReservationId(3)), 500, 500, "qrcodeid.png");
        }catch (Exception e){
            LOGGER.error("QR code error: " + e.toString());
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMultipart multipart = new MimeMultipart();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "utf-8");
        try {
            MimeBodyPart messageBodyContent = new MimeBodyPart();
            messageBodyContent.setContent(String.format(ReservationUtils.emailTemplate, employee.getFirstName(), employee.getLastName()),"text/html");
            multipart.addBodyPart(messageBodyContent);

            MimeBodyPart messageAttachment = new MimeBodyPart();
            DataSource source = new FileDataSource(QRCodeUtils.QR_CODE_IMAGE_PATH + "qrcodeid.png");
            messageAttachment.setDataHandler(new DataHandler(source));
            messageAttachment.setFileName("qrcodeid.png");
            multipart.addBodyPart(messageAttachment);

            message.setContent(multipart);
            messageHelper.setTo(emailTo);
            messageHelper.setFrom("${spring.mail.username}");
            messageHelper.setSubject("Reservation confirmation");
            javaMailSender.send(message);

            FileSystemUtils.deleteFile(QRCodeUtils.QR_CODE_IMAGE_PATH + "qrcodeid.png");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
