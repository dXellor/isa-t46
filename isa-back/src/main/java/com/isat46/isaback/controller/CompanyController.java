package com.isat46.isaback.controller;

import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

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
}
