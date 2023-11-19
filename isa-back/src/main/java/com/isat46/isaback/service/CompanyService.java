package com.isat46.isaback.service;

import com.isat46.isaback.dto.address.AddressDto;
import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.dto.company.CompanyRegistrationDto;
import com.isat46.isaback.mappers.CompanyMapper;
import com.isat46.isaback.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.isat46.isaback.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;

    public Page<CompanyDto> findAllPaged(Pageable page){
        return companyRepository.findAll(page).map(CompanyMapper::CompanyToCompanyDto);
    }

    public CompanyDto registerNewCompany(CompanyRegistrationDto companyRegistrationDto){
        AddressDto addressDto = addressService.addNewAddress(companyRegistrationDto.getAddress());
        companyRegistrationDto.setAddress(addressDto);
        Company company = companyRepository.save(CompanyMapper.CompanyRegistrationDtoToCompany(companyRegistrationDto));
        return CompanyMapper.CompanyToCompanyDto(company);
    }
}

