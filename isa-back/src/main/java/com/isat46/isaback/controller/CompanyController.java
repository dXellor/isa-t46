package com.isat46.isaback.controller;

import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.dto.company.CompanyRegistrationDto;
import com.isat46.isaback.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @Operation(summary = "get page of all companies", description = "get page of all companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "page of companies returned successfully")
    })
    @GetMapping(value = "/all")
    public ResponseEntity<Page<CompanyDto>> getCompaniesPage(Pageable page){
        Page<CompanyDto> companiesPage = companyService.findAllPaged(page);
        return new ResponseEntity<>(companiesPage, HttpStatus.OK);
    }

    @Operation(summary = "create new company", description = "create new company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "page of companies returned successfully"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDto> registerNewCompany(@Parameter(required = true) @Valid @RequestBody CompanyRegistrationDto companyRegistrationDto){
        CompanyDto newCompany = companyService.registerNewCompany(companyRegistrationDto);
        return new ResponseEntity<>(newCompany, HttpStatus.OK);
    }
}
