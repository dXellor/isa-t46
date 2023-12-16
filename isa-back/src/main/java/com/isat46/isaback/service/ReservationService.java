package com.isat46.isaback.service;

import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.dto.company.CompanyInfoDto;
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

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    public Page<ReservationDto> findAllPaged(Pageable page){
        return reservationRepository.findAll(page).map(ReservationMapper::ReservationToReservationDto);
    }

    public ReservationDto createNewReservation(ReservationDto reservationDto, String adminEmail){
        UserDto admin = userService.findByEmail(adminEmail);
        if(admin == null)
            return null;

        CompanyDto company = companyService.findCompanyByAdminId(admin.getId());
        if(company == null)
            return null;

        reservationDto.setCompanyAdmin(admin);
        reservationDto.setCompany(CompanyMapper.CompanyDtoToCompanyInfoDto(company));
        reservationDto.setStatus("APPOINTMENT");
        Reservation reservation = reservationRepository.save(ReservationMapper.ReservationDtoToReservation(reservationDto));
        return ReservationMapper.ReservationToReservationDto(reservation);
    }

    public ReservationDto updateReservation(ReservationDto reservationDto, String employeeEmail){
        UserDto employee = userService.findByEmail(employeeEmail);
        if(employee == null)
            return null;

        reservationDto.setEmployee(employee);
        reservationDto.setStatus("PENDING");
        Reservation reservation = reservationRepository.save(ReservationMapper.ReservationDtoToReservation(reservationDto));
        return ReservationMapper.ReservationToReservationDto(reservation);
    }
}
