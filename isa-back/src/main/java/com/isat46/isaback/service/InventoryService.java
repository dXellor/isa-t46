package com.isat46.isaback.service;

import com.isat46.isaback.dto.inventory.InventoryItemDto;
import com.isat46.isaback.dto.reservation.ReservationItemDto;
import com.isat46.isaback.mappers.InventoryItemMapper;
import com.isat46.isaback.model.InventoryItem;
import com.isat46.isaback.model.ReservationItem;
import com.isat46.isaback.repository.InventoryRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    protected final Log LOGGER = LogFactory.getLog(getClass());

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
        return inventoryItem != null && inventoryItem.getCount() >= reservationItemDto.getCount();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Boolean reduceStock(ReservationItemDto reservationItemDto){
        try {
            if(isInStock(reservationItemDto)){
                InventoryItem inventoryItem = inventoryRepository.findById(reservationItemDto.getInventoryItem().getId()).orElseGet(null);
                inventoryItem.setCount(inventoryItem.getCount() - reservationItemDto.getCount());
                inventoryRepository.save(inventoryItem);
                return true;
            }
        } catch (OptimisticLockException ex){
            LOGGER.error("Optimistic lock exception when reducing stock: " + ex);
            return false;
        }

        return false;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Boolean returnToStock(ReservationItem reservationItem){
        try {
            InventoryItem inventoryItem = inventoryRepository.findById(reservationItem.getInventoryItem().getId()).orElseGet(null);
            inventoryItem.setCount(inventoryItem.getCount() + reservationItem.getCount());
            inventoryRepository.save(inventoryItem);
            return true;
        } catch (OptimisticLockException ex){
            LOGGER.error("Optimistic lock exception when reducing stock: " + ex);
            return false;
        }
    }

    public InventoryItemDto addInventoryItem(InventoryItemDto inventoryItemDto){
        InventoryItem inventoryItem = InventoryItemMapper.InventoryItemDtoToInventoryItem(inventoryItemDto);
        return InventoryItemMapper.InventoryItemToInventoryItemDto(inventoryRepository.save(inventoryItem));
    }

    public void removeInventoryItem(int id){
        inventoryRepository.deleteById(id);
    }

    public InventoryItemDto updateInventoryItem(InventoryItemDto inventoryItemDto){
        return InventoryItemMapper.InventoryItemToInventoryItemDto(inventoryRepository.save(InventoryItemMapper.InventoryItemDtoToInventoryItem(inventoryItemDto)));
    }
}
