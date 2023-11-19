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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@CrossOrigin(origins = "*")
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
            @ApiResponse(responseCode = "201", description = "company created successfully"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasRole('SYSADMIN')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDto> registerNewCompany(@Parameter(required = true) @Valid @RequestBody CompanyRegistrationDto companyRegistrationDto){
        CompanyDto newCompany = companyService.registerNewCompany(companyRegistrationDto);
        return new ResponseEntity<>(newCompany, HttpStatus.CREATED);
    }

    @Operation(summary = "update company information", description = "update company information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "company info updated successfully"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasAnyRole('COMPADMIN', 'SYSADMIN')")
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDto> updateCompany(@Parameter(required = true) @Valid @RequestBody CompanyDto companyDto){
        CompanyDto updatedCompany = companyService.updateCompany(companyDto);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }
}
