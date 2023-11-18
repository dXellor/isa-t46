package com.isat46.isaback.aspect;

import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.model.VerificationToken;
import com.isat46.isaback.repository.VerificationTokenRepository;
import com.isat46.isaback.util.VerificationTokenUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Aspect
public class EmailAspect {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

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
}
