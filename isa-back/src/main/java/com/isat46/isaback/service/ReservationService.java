package com.isat46.isaback.service;

import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.dto.reservation.AppointmentCreationDto;
import com.isat46.isaback.dto.reservation.ReservationCreationDto;
import com.isat46.isaback.dto.reservation.ReservationDto;
import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.mappers.CompanyMapper;
import com.isat46.isaback.mappers.ReservationMapper;
import com.isat46.isaback.model.Reservation;
import com.isat46.isaback.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.isat46.isaback.util.ReservationUtils;

import java.time.LocalDateTime;
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

        reservationDto.setEmployee(employee);
        reservationDto.setStatus("PENDING");
        reservationDto.setNote(reservationCreationDto.getNote());
        Reservation reservation = reservationRepository.save(ReservationMapper.ReservationDtoToReservation(reservationDto));
        reservationItemService.addReservationItems(reservationCreationDto.getReservationItems(), reservation);
        return ReservationMapper.ReservationToReservationDto(reservation);
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
}
