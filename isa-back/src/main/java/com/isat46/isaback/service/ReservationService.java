package com.isat46.isaback.service;

import com.google.zxing.WriterException;
import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.dto.reservation.*;
import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.mappers.CompanyMapper;
import com.isat46.isaback.mappers.ReservationMapper;
import com.isat46.isaback.model.Reservation;
import com.isat46.isaback.model.enums.ReservationStatus;
import com.isat46.isaback.repository.ReservationRepository;
import com.isat46.isaback.util.FileSystemUtils;
import com.isat46.isaback.util.QRCodeUtils;
import com.isat46.isaback.util.PositionSimulatorCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.isat46.isaback.util.ReservationUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import javax.persistence.OptimisticLockException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ReservationItemService reservationItemService;

    @Autowired
    MqttService mqttService;
    
    protected final Log LOGGER = LogFactory.getLog(getClass());

    public Page<ReservationDto> findAllPaged(Pageable page){
        return reservationRepository.findAll(page).map(ReservationMapper::ReservationToReservationDto);
    }
    
    @Transactional
    public ReservationDto createNewAppointment(AppointmentCreationDto appointmentCreationDto){
        UserDto admin = userService.findOne(appointmentCreationDto.getCompanyAdminId());
        if(admin == null)
            return null;

        CompanyDto company = companyService.findCompanyByAdminId(admin.getId());
        if(company == null)
            return null;

        List<Reservation> adminReservations = reservationRepository.findByCompanyAdminId(appointmentCreationDto.getCompanyAdminId());
        if(doesIntertwineWithReservations(appointmentCreationDto.getDateTime(), appointmentCreationDto.getDuration(), adminReservations)){
            //Reservation intertwines with other reservation in system (change admin or time and date)
            return null;
        }

        ReservationDto reservationDto = ReservationMapper.AppointmentCreationDtoToReservationDto(appointmentCreationDto);
        reservationDto.setCompanyAdmin(admin);
        reservationDto.setCompany(CompanyMapper.CompanyDtoToCompanyInfoDto(company));
        reservationDto.setStatus("APPOINTMENT");
        
        try {
            Reservation reservation = reservationRepository.save(ReservationMapper.ReservationDtoToReservation(reservationDto));
            return ReservationMapper.ReservationToReservationDto(reservation);
        } catch (OptimisticLockException ex){
            LOGGER.error("Optimistic lock exception creating new appointment: " + ex);
            return null;
        }
    }
  
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ReservationDto createReservationWithPredefinedAppointment(ReservationCreationDto reservationCreationDto, String employeeEmail){
        UserDto employee = userService.findByEmail(employeeEmail);
        if(employee == null)
            return null;

        if(employee.getPenalPoints() > 2){
            throw new NotFoundException("Reservation not possible! You have 3 or more penal points.");
        }

        ReservationDto reservationDto = ReservationMapper.ReservationToReservationDto(reservationRepository.findById(reservationCreationDto.getReservationId()).orElseGet(null));
        if(reservationDto == null)
            //Predefined appointment does not exist
            return null;

        if(!inventoryService.areReservationItemsInStock(reservationCreationDto.getReservationItems()))
            //Items are not in stock
            return null;

        try {
            Reservation reservation = updateReservationToPending(reservationDto, employee, reservationCreationDto);
            reservationItemService.addReservationItems(reservationCreationDto.getReservationItems(), reservation);
            return ReservationMapper.ReservationToReservationDto(reservation);
        } catch (OptimisticLockException ex){
            LOGGER.error("Optimistic lock exception updating reservation: " + ex);
            return null;
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Reservation updateReservationToPending(ReservationDto reservationDto, UserDto employee, ReservationCreationDto reservationCreationDto){
        reservationDto.setEmployee(employee);
        reservationDto.setStatus("PENDING");
        reservationDto.setNote(reservationCreationDto.getNote());
        return reservationRepository.save(ReservationMapper.ReservationDtoToReservation(reservationDto));
    }

    public List<ReservationDto> findByDay(String companyAdminEmail, int year, int month, int day)
    {
        UserDto companyAdmin = userService.findByEmail(companyAdminEmail);

        return ReservationMapper.ReservationsToReservationDtos(reservationRepository.findByDay(companyAdmin.getId(), year, month, day));
    }

    public List<ReservationDto> findByWeek(String companyAdminEmail, int year, int month, int day)
    {
        UserDto companyAdmin = userService.findByEmail(companyAdminEmail);

        return ReservationMapper.ReservationsToReservationDtos(reservationRepository.findByWeek(companyAdmin.getId(), year, month, day));
    }

    public List<ReservationDto> findByMonthAndYear(String companyAdminEmail, int year, int month)
    {
        UserDto companyAdmin = userService.findByEmail(companyAdminEmail);

        return ReservationMapper.ReservationsToReservationDtos(reservationRepository.findByMonthAndYear(companyAdmin.getId(), year, month));
    }

    public List<ReservationDto> findByYear(String companyAdminEmail, int year)
    {
        UserDto companyAdmin = userService.findByEmail(companyAdminEmail);

        return ReservationMapper.ReservationsToReservationDtos(reservationRepository.findByYear(companyAdmin.getId(), year));
    }
  
    private Boolean doesIntertwineWithReservations(LocalDateTime dateTimeToCheck, long durationToCheck, List<Reservation> reservations){
        Boolean intertwines = false;
        LocalDateTime startToCheck = dateTimeToCheck;
        LocalDateTime endToCheck = dateTimeToCheck.plusMinutes(durationToCheck);

        for(Reservation reservation : reservations){
            LocalDateTime start = reservation.getDateTime();
            LocalDateTime end = start.plusMinutes(reservation.getDuration());

            if(ReservationUtils.isDateTimeIntertwined(startToCheck, endToCheck, start, end)){
                intertwines = true;
                break;
            }
        }

        return intertwines;
    }

    public List<ReservationDto> offerOutOfOrderReservations(int companyId, LocalDate wantedDate){
        List<LocalDateTime> offeredTimes = new ArrayList<>();
        LocalDateTime start = LocalDateTime.of(wantedDate, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(wantedDate, LocalTime.MAX);

        List<Reservation> scheduledReservations = reservationRepository.findByCompanyIdAndDateTimeBetweenAndStatusIn(
                companyId,
                start,
                end,
                Arrays.asList(ReservationStatus.APPOINTMENT, ReservationStatus.PENDING)
        );

        var companyDto = companyService.findById(companyId);
        LocalTime startWork = companyDto.getStartWork();
        LocalTime endWork = companyDto.getEndWork();
        List<LocalDateTime> allPossibleTimes = generateTimeSlots(startWork, endWork, 30, wantedDate);

        for (LocalDateTime possibleDateTime : allPossibleTimes) {
            if (!doesIntertwineWithReservations(possibleDateTime, 30, scheduledReservations)) {
                offeredTimes.add(possibleDateTime);
            }
        }

        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (LocalDateTime offeredTime : offeredTimes) {
            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setDateTime(offeredTime);
            reservationDto.setDuration(30);
            reservationDtos.add(reservationDto);
        }

        return reservationDtos;

    }

    private List<LocalDateTime> generateTimeSlots(LocalTime startTime, LocalTime endTime, int slotDurationMinutes, LocalDate wantedDate) {
        List<LocalDateTime> timeSlots = new ArrayList<>();
        LocalDateTime wantedDateTime = LocalDateTime.of(wantedDate, startTime);

        while (!wantedDateTime.toLocalTime().isAfter(endTime.minusMinutes(slotDurationMinutes))) {
            timeSlots.add(wantedDateTime);
            wantedDateTime = wantedDateTime.plusMinutes(slotDurationMinutes).plusSeconds(1);
        }

        return timeSlots;
    }
    @Transactional
    public ReservationDto createReservationWithOutOfOrderAppointment(OutOfOrderReservationDto outOfOrderReservationDto, String userEmail){
        ReservationDto reservationDto = outOfOrderReservationDto.getReservation();
        List<ReservationItemDto> selectedEquipment = outOfOrderReservationDto.getReservationItems();

        UserDto loggedUser = userService.findByEmail(userEmail);
        if(loggedUser.getPenalPoints() > 2){
            throw new NotFoundException("Reservation not possible! You have 3 or more penal points.");
        }
        CompanyDto company = companyService.findById(reservationDto.getCompany().getId());
        List<UserDto> admins = company.getAdmins();
        UserDto companyAdmin = null;
        if (admins != null && !admins.isEmpty()) {
            companyAdmin = admins.getFirst();
        } else {
            throw new NotFoundException("No company admin avaliable!");
        }

        if(companyAdmin==null)
            throw new NotFoundException("No company admin avaliable!");

        if(!inventoryService.areReservationItemsInStock(selectedEquipment))
            throw new NotFoundException("Not enough Equipment in stock!");

        reservationDto.setEmployee(loggedUser);
        reservationDto.setNote("out of order appointment");
        reservationDto.setStatus("APPOINTMENT");
        reservationDto.setCompany(CompanyMapper.CompanyDtoToCompanyInfoDto(company));
        reservationDto.setCompanyAdmin(companyAdmin);

        try {
            Reservation reservation = reservationRepository.save(ReservationMapper.ReservationDtoToReservation(reservationDto));
            reservationItemService.addReservationItems(selectedEquipment, reservation);
            return ReservationMapper.ReservationToReservationDto(reservation);
        } catch (OptimisticLockException ex) {
            LOGGER.error("Optimistic lock exception updating reservation: " + ex);
            return null;
        }
    }

    public Page<ReservationDto> findByEmployee(Pageable page, String email){
        return reservationRepository.findByEmployee(page, email).map(ReservationMapper::ReservationToReservationDto);
    }

    public Page<ReservationDto> findByCompanyAdmin(Pageable page){
        return reservationRepository.findByEmployeeIsNull(page).map(ReservationMapper::ReservationToReservationDto);
    }

    public List<ReservationDto> findAvailableAppointmentsByCompany(int companyId){
        List<Reservation> appointments = reservationRepository.findAvailableAppointmentsByCompany(companyId);
        return ReservationMapper.ReservationsToReservationDtos(appointments);
    }

    public ReservationDto cancelReservation(int reservationId, String employeeEmail){
        Reservation reservationToCancel = reservationRepository.findReservationToCancel(reservationId, employeeEmail);
        if(reservationToCancel == null){
            //There is no reservation to cancel
            return null;
        }

        if(reservationToCancel.getDateTime().isBefore(LocalDateTime.now())){
            reservationToCancel.setStatus(ReservationStatus.CANCELED);
        }else{
            reservationToCancel.setStatus(ReservationStatus.APPOINTMENT);
        }

        reservationItemService.removeReservationItems(reservationToCancel);
        userService.punishUserForCancelation(reservationToCancel);
        reservationToCancel.setEmployee(null);
        reservationRepository.save(reservationToCancel);

        return ReservationMapper.ReservationToReservationDto(reservationToCancel);
    }
    
    @Transactional
    @Scheduled(fixedDelay = 3600000)
    public void invalidateOutdatedReservations() {
        int updatedRows = reservationRepository.invalidateOutdatedReservations();
        LOGGER.info("Invalidated " + updatedRows + " outdated reservations");

        List<Reservation> expiredReservations = reservationRepository.findExpiredReservations();
        for (Reservation reservation : expiredReservations) {
            reservationItemService.removeReservationItems(reservation);
            userService.punishUser(reservation.getEmployee(), 2);
            reservation.setEmployee(null);
            reservationRepository.save(reservation);
        }
    }
    @Transactional
    public void startTracking(Integer reservationId, String employeeEmail){
        Reservation reservation = reservationRepository.findById(reservationId).orElseGet(null);
        if(reservation == null){
//            || !reservation.getEmployee().getEmail().equals(employeeEmail)
            return;
        }

        PositionSimulatorCommand command = new PositionSimulatorCommand("BEGIN", reservationId, 1.2f, 1.2f, 1.2f, 1.2f, mqttService.delay);
        mqttService.publish(mqttService.MQTT_COMMAND_TOPIC, command.toString());
    }
    public List<ReservationDto> getCompletedReservationsForUser(String userEmail){
        List<Reservation> reservations = reservationRepository.findCompletedByUser(userEmail);
        return ReservationMapper.ReservationsToReservationDtos(reservations);
    }

    public List<ReservationDto> sortReservationsByDurationAndDate(String userEmail, String orderByDuration, String orderByDateTime) {
        List<Reservation> reservations = reservationRepository.orderByDurationAndDateTime(userEmail, orderByDuration, orderByDateTime);
        return ReservationMapper.ReservationsToReservationDtos(reservations);
    }
    
    public ReservationDto confirmReservation(int reservationId, String employeeEmail){
        Reservation reservation = reservationRepository.findReservationToCancel(reservationId, employeeEmail);
        if(reservation == null) {
            return null;
        }
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);
        return ReservationMapper.ReservationToReservationDto(reservation);
    }
    
    private boolean isAdminDelivering(String email){
        return reservationRepository.findInProgressDeliveryByAdmin(email) != null;
    }
    @Transactional
    public boolean startDelivery(int reservationId) {
        Reservation reservation = reservationRepository.findReservationToDeliver(reservationId);
        if(reservation == null) {
            return false;
        }
        try {
            String email = reservation.getCompanyAdmin().getEmail();
            if (isAdminDelivering(email)) {
                return false;
            }
            reservation.setStatus(ReservationStatus.IN_PROGRESS);
            reservationRepository.save(reservation);
            return true;
            
        } catch (OptimisticLockException ex) {
            LOGGER.error("Company admin " + reservation.getCompanyAdmin().getFirstName() + " cannot be present at multiple deliveries at once!");
            return false;
        }
        
    }
    @Transactional
    public Reservation completeDelivery(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if(reservation == null) {
            return null;
        }
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<?> future = executor.submit(() -> {
                try {
                    Thread.sleep(reservation.getDuration() * 60 * 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            future.get();
            reservation.setStatus(ReservationStatus.COMPLETED);
            reservationRepository.save(reservation);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Sleep interrupted");
        }
        return reservation;
    }

    
    public ReservationDto deliverEquipment(int reservationId) {
        if(!startDelivery(reservationId))
            return null;
        Reservation completedReservation = completeDelivery(reservationId);
        return ReservationMapper.ReservationToReservationDto(completedReservation);
    }
    
    public Page<ReservationDto> findConfirmedReservationsByAdmin(String email, Pageable page){
        return reservationRepository.findConfirmedReservationsByAdmin(email, page).map(ReservationMapper::ReservationToReservationDto);
    }

    public ReservationQRCodeDto generateQRCodeForReservation(ReservationDto reservationDto, int width, int height)
            throws WriterException, IOException {
        String qrCodeText = ReservationUtils.getReservationInformation(reservationDto, reservationItemService.findReservationItemsByReservationId(reservationDto.getId()));
        String filename = "qrcodeid.png";

        QRCodeUtils.generateReservationQRCodeImage(qrCodeText, width, height, filename);
        byte[] qrCodeImageData = QRCodeUtils.getQRCodeImage(qrCodeText, width, height);
        FileSystemUtils.deleteFile(QRCodeUtils.QR_CODE_IMAGE_PATH + "qrcodeid.png");
        return new ReservationQRCodeDto(qrCodeImageData);
    }

    public List<ReservationDto> getPendingReservationsForUser(String userEmail){
        List<Reservation> reservations = reservationRepository.findPendingByUser(userEmail);
        return ReservationMapper.ReservationsToReservationDtos(reservations);
    }
    public List<ReservationDto> getCancelledReservationsForUser(String userEmail){
        List<Reservation> reservations = reservationRepository.findCancelledByUser(userEmail);
        return ReservationMapper.ReservationsToReservationDtos(reservations);
    }

    public List<ReservationDto> getConfirmedReservationsForUser(String userEmail){
        List<Reservation> reservations = reservationRepository.findConfirmedByUser(userEmail);
        return ReservationMapper.ReservationsToReservationDtos(reservations);
    }

    public ReservationDto confirmReservationByQRCode(ReservationQRCodeDto qrCodeDto) {
        String reservationInfo = QRCodeUtils.decodeQR(qrCodeDto.getQrCodeImageData());
        int reservationId = extractReservationId(reservationInfo);
        Reservation reservation = reservationRepository.findById(reservationId).orElseGet(null);

        if(reservation.getStatus() == ReservationStatus.COMPLETED) {
            throw new IllegalStateException();
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        reservationRepository.save(reservation);
        return ReservationMapper.ReservationToReservationDto(reservation);
    }

    private int extractReservationId(String reservationInfo) {
        String[] lines  = reservationInfo.split("\n");
        String id = lines[0].replace("Reservation Id: ", "");

        return Integer.parseInt(id);
    }
}
