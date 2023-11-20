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

    public Page<CompanyDto> searchByNameCityCountry(String name, String city, String country, Pageable page) {
        Page<Company> companies = companyRepository
                .findByNameIgnoreCaseContainingAndAddressCityIgnoreCaseContainingAndAddressCountryIgnoreCaseContaining(
                        name, city, country, page);

        if(!companies.hasContent()){
            return Page.empty();
        }

        return companies.map(CompanyMapper::CompanyToCompanyDto);
    }

    public Page<CompanyDto> filterByRatingRange(double minAverageRating, double maxAverageRating, Pageable page) {
        Page<Company> companies = companyRepository.findByAverageRatingBetween(minAverageRating, maxAverageRating, page);

        if (!companies.hasContent()) {
            return Page.empty();
        }

        return companies.map(CompanyMapper::CompanyToCompanyDto);
    }

    public CompanyDto findCompanyByAdminId(Integer adminId){
        Company company = companyRepository.findByAdminId(adminId);
        return CompanyMapper.CompanyToCompanyDto(company);
    }
}

