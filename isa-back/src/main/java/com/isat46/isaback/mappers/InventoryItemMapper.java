package com.isat46.isaback.mappers;

import com.isat46.isaback.dto.inventory.InventoryItemDto;
import com.isat46.isaback.model.InventoryItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryItemMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public InventoryItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static InventoryItemDto InventoryItemToInventoryItemDto(InventoryItem inventoryItem){
        return modelMapper.map(inventoryItem, InventoryItemDto.class);
    }

    public static InventoryItem InventoryItemDtoToInventoryItem(InventoryItemDto inventoryItemDto){
        return modelMapper.map(inventoryItemDto, InventoryItem.class);
    }
}
