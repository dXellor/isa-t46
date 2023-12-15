package com.isat46.isaback.mappers;

import com.isat46.isaback.dto.reservation.ReservationDto;
import com.isat46.isaback.model.Reservation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public ReservationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static ReservationDto ReservationToReservationDto(Reservation reservation){
        return modelMapper.map(reservation, ReservationDto.class);
    }

    public static Reservation ReservationDtoToReservation(ReservationDto reservationDto){
        return modelMapper.map(reservationDto, Reservation.class);
    }
}
