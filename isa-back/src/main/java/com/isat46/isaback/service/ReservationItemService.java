package com.isat46.isaback.service;

import com.isat46.isaback.dto.reservation.ReservationItemDto;
import com.isat46.isaback.mappers.ReservationItemMapper;
import com.isat46.isaback.repository.ReservationItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationItemService {

    @Autowired
    ReservationItemRepository reservationItemRepository;

    public List<ReservationItemDto> findReservationItemsByReservationId(int reservationId){
        return ReservationItemMapper.ReservationItemsToReservationItemDtos(reservationItemRepository.findReservationItemsByReservationId(reservationId));
    }

}
