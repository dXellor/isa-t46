package com.isat46.isaback.service;

import com.isat46.isaback.dto.reservation.ReservationItemDto;
import com.isat46.isaback.mappers.InventoryItemMapper;
import com.isat46.isaback.mappers.ReservationItemMapper;
import com.isat46.isaback.model.Reservation;
import com.isat46.isaback.model.ReservationItem;
import com.isat46.isaback.repository.ReservationItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Boolean removeReservationItems(Reservation reservation){
        List<ReservationItem> items = reservationItemRepository.findReservationItemsByReservationId(reservation.getId());
        for(ReservationItem item : items){
            inventoryService.returnToStock(item);
            reservationItemRepository.delete(item);
        }
        return true;
    }

    public Page<ReservationItemDto>  getPaged(Pageable page){
        return reservationItemRepository.findAll(page).map(ReservationItemMapper::ReservationItemToReservationItemDto);
    }

}
