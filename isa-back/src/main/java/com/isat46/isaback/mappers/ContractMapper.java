package com.isat46.isaback.mappers;

import com.google.zxing.qrcode.decoder.Mode;
import com.isat46.isaback.dto.address.AddressDto;
import com.isat46.isaback.dto.contract.ContractDto;
import com.isat46.isaback.model.Address;
import com.isat46.isaback.model.Contract;
import com.isat46.isaback.service.ContractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractMapper {
    
    private static ModelMapper modelMapper;
    
    @Autowired
    public ContractMapper(ModelMapper modelMapper){
        ContractMapper.modelMapper = modelMapper;
    }

    public static ContractDto ContractToContractDto(Contract contract){
        return modelMapper.map(contract, ContractDto.class);
    }

    public static Contract ContractDtoToContract(ContractDto contractDto){
        return modelMapper.map(contractDto, Contract.class);
    }
}
