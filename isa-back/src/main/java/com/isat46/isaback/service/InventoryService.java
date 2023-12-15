package com.isat46.isaback.service;

import com.isat46.isaback.dto.inventory.InventoryItemDto;
import com.isat46.isaback.mappers.InventoryItemMapper;
import com.isat46.isaback.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public Page<InventoryItemDto> findByCompanyIdPaged(int companyId, Pageable page){
        return inventoryRepository.findByCompanyId(companyId, page).map(InventoryItemMapper::InventoryItemToInventoryItemDto);
    }
}
