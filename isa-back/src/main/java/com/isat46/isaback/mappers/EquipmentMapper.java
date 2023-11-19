package com.isat46.isaback.mappers;

import com.isat46.isaback.dto.equipment.EquipmentDto;
import com.isat46.isaback.model.Equipment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EquipmentMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public EquipmentMapper(ModelMapper modelMapper) {
        EquipmentMapper.modelMapper = modelMapper;
    }

    public static EquipmentDto EquipmentToEquipmentDto(Equipment equipment){
        return modelMapper.map(equipment, EquipmentDto.class);
    }

    public static Equipment EquipmentDtoToEquipment(EquipmentDto equipmentDto){
        return modelMapper.map(equipmentDto, Equipment.class);
    }

    public static List<EquipmentDto> EquipmentListToEquipmentDtoList(Page<Equipment> equipmentList) {
        return equipmentList.stream()
                .map(equipment -> modelMapper.map(equipment, EquipmentDto.class))
                .collect(Collectors.toList());
    }

    public static List<Equipment> EquipmentDtoListToEquipmentList(Page<EquipmentDto> equipmentDtoList) {
        return equipmentDtoList.stream()
                .map(equipmentDto -> modelMapper.map(equipmentDto, Equipment.class))
                .collect(Collectors.toList());
    }



}
