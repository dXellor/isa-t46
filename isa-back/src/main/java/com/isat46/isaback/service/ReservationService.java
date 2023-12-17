package com.isat46.isaback.service;

import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.dto.company.CompanyInfoDto;
import com.isat46.isaback.dto.reservation.AppointmentCreationDto;
import com.isat46.isaback.dto.reservation.ReservationCreationDto;
import com.isat46.isaback.dto.reservation.ReservationDto;
import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.mappers.CompanyMapper;
import com.isat46.isaback.mappers.ReservationMapper;
import com.isat46.isaback.mappers.UserMapper;
import com.isat46.isaback.model.Reservation;
import com.isat46.isaback.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.isat46.isaback.mappers.ReservationMapper;

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

    public List<ReservationDto> findByDay(int year, int month, int day)
    {
        return ReservationMapper.ReservationsToReservationDtos(reservationRepository.findByDay(year, month, day));
    }

    public List<ReservationDto> findByWeek(int year, int month, int day)
    {
        return ReservationMapper.ReservationsToReservationDtos(reservationRepository.findByWeek(year, month, day));
    }

    public List<ReservationDto> findByMonthAndYear(int year, int month)
    {
        return ReservationMapper.ReservationsToReservationDtos(reservationRepository.findByMonthAndYear(year, month));
    }

    public List<ReservationDto> findByYear(int year)
    {
        return ReservationMapper.ReservationsToReservationDtos(reservationRepository.findByYear(year));
    }
}
