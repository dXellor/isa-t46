package com.isat46.isaback.service;

import com.isat46.isaback.dto.inventory.InventoryItemDto;
import com.isat46.isaback.dto.reservation.ReservationItemDto;
import com.isat46.isaback.mappers.InventoryItemMapper;
import com.isat46.isaback.model.InventoryItem;
import com.isat46.isaback.model.ReservationItem;
import com.isat46.isaback.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public Page<InventoryItemDto> findByCompanyIdPaged(int companyId, Pageable page){
        return inventoryRepository.findByCompanyId(companyId, page).map(InventoryItemMapper::InventoryItemToInventoryItemDto);
    }

    public Boolean areReservationItemsInStock(List<ReservationItemDto> reservationItems){
        Boolean allInStock = true;
        for(ReservationItemDto reservationItem : reservationItems){
            if(!isInStock(reservationItem)){
                allInStock = false;
                break;
            }
        }

        return allInStock;
    }

    private Boolean isInStock(ReservationItemDto reservationItemDto){
        InventoryItem inventoryItem = inventoryRepository.findById(reservationItemDto.getInventoryItem().getId()).orElseGet(null);
        return inventoryItem != null && inventoryItem.getCount() > reservationItemDto.getCount();
    }

    public Boolean reduceStock(ReservationItemDto reservationItemDto){
        if(isInStock(reservationItemDto)){
            InventoryItem inventoryItem = inventoryRepository.findById(reservationItemDto.getInventoryItem().getId()).orElseGet(null);
            inventoryItem.setCount(inventoryItem.getCount() - reservationItemDto.getCount());
            inventoryRepository.save(inventoryItem);
            return true;
        }

        return false;
    }

    public InventoryItemDto addInventoryItem(InventoryItemDto inventoryItemDto){
        InventoryItem inventoryItem = InventoryItemMapper.InventoryItemDtoToInventoryItem(inventoryItemDto);
        return InventoryItemMapper.InventoryItemToInventoryItemDto(inventoryRepository.save(inventoryItem));
    }
}
