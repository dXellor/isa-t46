package com.isat46.isaback.service;

import com.isat46.isaback.dto.equipment.EquipmentDto;
import com.isat46.isaback.mappers.EquipmentMapper;
import com.isat46.isaback.model.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.isat46.isaback.repository.EquipmentRepository;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public Page<EquipmentDto> findAllPaged(Pageable page){
        Page<Equipment> equipmentPage = equipmentRepository.findAll(page);
        return equipmentPage.map(EquipmentMapper::EquipmentToEquipmentDto);
    }
    public Page<EquipmentDto> findEquipmentByCompanyId(Integer companyId, Pageable pageable){
        Page<Equipment> equipmentPage = equipmentRepository.findEquipmentByCompanyId(companyId, pageable);
        return equipmentPage.map(EquipmentMapper::EquipmentToEquipmentDto);
    }


}
