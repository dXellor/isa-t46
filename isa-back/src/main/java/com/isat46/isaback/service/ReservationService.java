package com.isat46.isaback.service;

import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.dto.company.CompanyInfoDto;
import com.isat46.isaback.dto.equipment.EquipmentDto;
import com.isat46.isaback.dto.reservation.*;
import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.mappers.CompanyMapper;
import com.isat46.isaback.mappers.ReservationMapper;
import com.isat46.isaback.model.Equipment;
import com.isat46.isaback.model.Reservation;
import com.isat46.isaback.model.ReservationItem;
import com.isat46.isaback.model.User;
import com.isat46.isaback.model.enums.ReservationStatus;
import com.isat46.isaback.repository.ReservationRepository;
import com.isat46.isaback.util.PositionSimulatorCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.isat46.isaback.mappers.ReservationMapper;
import com.isat46.isaback.util.ReservationUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import javax.persistence.OptimisticLockException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Reservation reservation = reservationRepository.save(ReservationMapper.ReservationDtoToReservation(reservationDto));
        return ReservationMapper.ReservationToReservationDto(reservation);
    }

    public ReservationDto createReservationWithPredefinedAppointment(ReservationCreationDto reservationCreationDto, String employeeEmail){
        UserDto employee = userService.findByEmail(employeeEmail);
        if(employee == null)
            return null;

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
            wantedDateTime = wantedDateTime.plusMinutes(slotDurationMinutes);
        }

        return timeSlots;
    }

    public ReservationDto createReservationWithOutOfOrderAppointment(OutOfOrderReservationDto outOfOrderReservationDto, String userEmail){
        ReservationDto reservationDto = outOfOrderReservationDto.getReservation();
        List<ReservationItemDto> selectedEquipment = outOfOrderReservationDto.getReservationItems();

        UserDto loggedUser = userService.findByEmail(userEmail);
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

        Reservation reservation = reservationRepository.save(ReservationMapper.ReservationDtoToReservation(reservationDto));
        reservationItemService.addReservationItems(selectedEquipment, reservation);
        return ReservationMapper.ReservationToReservationDto(reservation);
    }

    public Page<ReservationDto> findByEmployee(Pageable page, String email){
        return reservationRepository.findByEmployeeEmailAndEmployeeNotNull(page, email).map(ReservationMapper::ReservationToReservationDto);
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
        if(reservation == null || !reservation.getEmployee().getEmail().equals(employeeEmail)){
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
    
    private boolean isAdminDelivering(int adminId){
        return reservationRepository.findInProgressDeliveryByAdmin(adminId) != null;
    }

    public ReservationDto deliverEquipment(int reservationId){
        Reservation reservation = reservationRepository.findReservationToDeliver(reservationId);
        if(reservation == null) {
            return null;
        }
        try {
            int adminId = reservation.getCompanyAdmin().getId();
            if (isAdminDelivering(adminId)) {
                throw new OptimisticLockException();
            }

            reservation.setStatus(ReservationStatus.COMPLETED);
            reservationRepository.save(reservation);

            return ReservationMapper.ReservationToReservationDto(reservation);
        }
        catch (OptimisticLockException ex) {
            LOGGER.error("Company admin " + reservation.getCompanyAdmin().getFirstName() + " cannot be present at multiple deliveries at once!");
            return null;
        }
    }


}
