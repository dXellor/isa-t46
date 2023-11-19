package com.isat46.isaback.mappers;

import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.model.Company;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    private static ModelMapper modelMapper;

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
}
