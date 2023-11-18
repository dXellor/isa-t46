package com.isat46.isaback.service;

import com.isat46.isaback.dto.address.AddressDto;
import com.isat46.isaback.mappers.AddressMapper;
import com.isat46.isaback.model.Address;
import com.isat46.isaback.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressDto findAddressByCompanyId(Integer companyId){
        Address address = addressRepository.findAddressByCompanyId(companyId);
        if (address != null) {
            return AddressMapper.AddressToAddressDto(address);
        } else {
            throw new NotFoundException("Address not found for this company id :: " + companyId);
        }
    }
}
