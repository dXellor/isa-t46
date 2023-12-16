package com.isat46.isaback.mappers;

import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.dto.company.CompanyInfoDto;
import com.isat46.isaback.dto.company.CompanyRegistrationDto;
import com.isat46.isaback.model.Company;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    private static ModelMapper modelMapper;

    public CompanyMapper(){
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
    }

    @Autowired
    public CompanyMapper(ModelMapper modelMapper) {
        CompanyMapper.modelMapper = modelMapper;
    }

    public static CompanyDto CompanyToCompanyDto(Company company){
        return modelMapper.map(company, CompanyDto.class);
    }

    public static Company CompanyDtoToCompany(CompanyDto companyDto){
        return modelMapper.map(companyDto, Company.class);
    }

    public static Company CompanyRegistrationDtoToCompany(CompanyRegistrationDto companyRegistrationDto){
        return modelMapper.map(companyRegistrationDto, Company.class);
    }

    public static CompanyInfoDto CompanyDtoToCompanyInfoDto(CompanyDto companyDto){
        return modelMapper.map(companyDto, CompanyInfoDto.class);
    }
}
