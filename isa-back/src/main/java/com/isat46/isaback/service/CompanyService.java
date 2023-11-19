package com.isat46.isaback.service;

import com.isat46.isaback.dto.address.AddressDto;
import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.dto.company.CompanyRegistrationDto;
import com.isat46.isaback.dto.company.CompanySearchDto;
import com.isat46.isaback.mappers.CompanyMapper;
import com.isat46.isaback.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.isat46.isaback.repository.CompanyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;

    public Page<CompanyDto> findAllPaged(Pageable page){
        return companyRepository.findAll(page).map(CompanyMapper::CompanyToCompanyDto);
    }

    public Page<CompanyDto> findCompaniesThatHaveEquipment(int equipmentId, Pageable page){
        return companyRepository.findCompaniesThatHaveEquipment(equipmentId, page).map(CompanyMapper::CompanyToCompanyDto);
    }

    public CompanyDto registerNewCompany(CompanyRegistrationDto companyRegistrationDto){
        AddressDto addressDto = addressService.addNewAddress(companyRegistrationDto.getAddress());
        companyRegistrationDto.setAddress(addressDto);
        Company company = companyRepository.save(CompanyMapper.CompanyRegistrationDtoToCompany(companyRegistrationDto));
        return CompanyMapper.CompanyToCompanyDto(company);
    }

    public CompanyDto updateCompany(CompanyDto companyDto){
        Company updatedCompany = companyRepository.save(CompanyMapper.CompanyDtoToCompany(companyDto));
        return CompanyMapper.CompanyToCompanyDto(updatedCompany);
    }

    public List<CompanySearchDto> searchByNameCityCountry(String name, String city, String country) {
        List<Company> companies = companyRepository
                .findByNameIgnoreCaseContainingAndAddressCityIgnoreCaseContainingAndAddressCountryIgnoreCaseContaining(
                        name, city, country);
        if(companies == null){
            List<Company> emptyCompanies= new ArrayList<>();
            return mapToDtoList(emptyCompanies);
        }
        return mapToDtoList(companies);
    }

    private List<CompanySearchDto> mapToDtoList(List<Company> companies) {
        return companies.stream()
                .map(CompanySearchDto::new)
                .collect(Collectors.toList());
    }
}

