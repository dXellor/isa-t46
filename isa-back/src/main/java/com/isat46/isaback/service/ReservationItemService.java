package com.isat46.isaback.service;

import com.isat46.isaback.dto.reservation.ReservationItemDto;
import com.isat46.isaback.mappers.ReservationItemMapper;
import com.isat46.isaback.model.Reservation;
import com.isat46.isaback.model.ReservationItem;
import com.isat46.isaback.repository.ReservationItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationItemService {

    @Autowired
    ReservationItemRepository reservationItemRepository;

    @Autowired
    InventoryService inventoryService;

    public List<ReservationItemDto> findReservationItemsByReservationId(int reservationId){
        return ReservationItemMapper.ReservationItemsToReservationItemDtos(reservationItemRepository.findReservationItemsByReservationId(reservationId));
    }

    public Boolean addReservationItems(List<ReservationItemDto> reservationItems, Reservation reservation){
        // TODO: transaction, error prevention
        for(ReservationItemDto itemDto : reservationItems){
            inventoryService.reduceStock(itemDto);
            ReservationItem reservationItem = ReservationItemMapper.ReservationItemDtoToReservationItem(itemDto);
            reservationItem.setReservation(reservation);
            reservationItemRepository.save(reservationItem);
        }

        return true;
    }

}
