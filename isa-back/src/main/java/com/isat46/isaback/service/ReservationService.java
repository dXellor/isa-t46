package com.isat46.isaback.service;

import com.isat46.isaback.dto.reservation.ReservationDto;
import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.mappers.ReservationMapper;
import com.isat46.isaback.model.Reservation;
import com.isat46.isaback.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserService userService;

    public ReservationDto createNewReservation(ReservationDto reservationDto, String adminEmail){
        UserDto admin = userService.findByEmail(adminEmail);
        if(admin == null)
            return null;

        reservationDto.setCompanyAdmin(admin);
        reservationDto.setStatus("PENDING");
        Reservation reservation = reservationRepository.save(ReservationMapper.ReservationDtoToReservation(reservationDto));
        return ReservationMapper.ReservationToReservationDto(reservation);
    }
}
