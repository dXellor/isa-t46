package com.isat46.isaback.mappers;

import com.isat46.isaback.dto.address.AddressDto;
import com.isat46.isaback.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public AddressMapper(ModelMapper modelMapper) {
        AddressMapper.modelMapper = modelMapper;
    }

    public static AddressDto AddressToAddressDto(Address address){
        return modelMapper.map(address, AddressDto.class);
    }

    public static Address AddressDtoToAddress(AddressDto addressDto){
        return modelMapper.map(addressDto, Address.class);
    }
}
