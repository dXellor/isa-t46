package com.isat46.isaback.service;

import com.isat46.isaback.dto.contract.ContractDto;
import com.isat46.isaback.mappers.ContractMapper;
import com.isat46.isaback.model.Contract;
import com.isat46.isaback.repository.ContractRepository;
import com.isat46.isaback.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ContractService {
    
    @Autowired
    private ContractRepository contractRepository;
    
    @Autowired
    private InventoryRepository inventoryRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Transactional
    public void createContract(ContractDto contractDto){
        contractRepository.deleteByHospital(contractDto.getHospital());
        contractRepository.save(ContractMapper.ContractDtoToContract(contractDto));
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkInventoryAndDeliver() {
        List<Contract> contracts = contractRepository.findAll();

        for (Contract contract : contracts) {
            if (isTwoDaysBeforeDeliveryDay(contract)) {
                if (!inventoryRepository.isEquipmentInStock(contract.getEquipment(), contract.getCompanyName(), contract.getCount())) {
                    notificationService.sendNoInventoryMessage(contract);
                }
            } else if (isDeliveryDay(contract)) {
                if (inventoryRepository.isEquipmentInStock(contract.getEquipment(), contract.getCompanyName(), contract.getCount())) {
                    inventoryRepository.subtractEquipmentCount(contract.getEquipment(), contract.getCompanyName(), contract.getCount());
                    notificationService.sendSuccessfulDeliveryMessage(contract);
                } else {
                    notificationService.sendNoInventoryMessage(contract);
                }
            }
        }
    }

    private Boolean isTwoDaysBeforeDeliveryDay(Contract contract){
        return LocalDate.now().plusDays(2).getDayOfMonth() == contract.getDay();
    }

    private Boolean isDeliveryDay(Contract contract){
        return LocalDate.now().getDayOfMonth() == contract.getDay();
    }
}
