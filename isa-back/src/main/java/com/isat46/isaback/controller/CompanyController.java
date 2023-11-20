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

    @Operation(summary = "get page of companies that have equipment", description = "get page of companies that have equipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "page of companies that have equipment returned successfully")
    })
    @PreAuthorize("hasAnyRole('USER', 'SYSADMIN', 'COMPADMIN')")
    @GetMapping(value = "/{equipmentId}")
    public ResponseEntity<Page<CompanyDto>> getCompaniesThatHaveEquipment(@PathVariable Integer equipmentId, Pageable page){
        Page<CompanyDto> companiesPage = companyService.findCompaniesThatHaveEquipment(equipmentId, page);
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

    @Operation(summary = "search by name, city, country", description = "search by name, city, country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "searched successfully"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @GetMapping("/searchByNameCityCountry")
    public ResponseEntity<Page<CompanyDto>> searchByNameCityCountry(@RequestParam(required = false) String name, @RequestParam(required = false) String city, @RequestParam(required = false) String country, Pageable page) {
        Page<CompanyDto> searchedDtos =  companyService.searchByNameCityCountry(name, city, country, page);
        return new ResponseEntity<>(searchedDtos, HttpStatus.OK);
    }

    @Operation(summary = "filter by min and max average rating", description = "filter by min and max average rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "filtered successfully"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @GetMapping("/filterByRating")
    public ResponseEntity<Page<CompanyDto>> filterByRatingRange(@RequestParam(required = false) Double avgRatingMin, @RequestParam(required = false) Double avgRatingMax, Pageable page) {
        Page<CompanyDto> searchedDtos =  companyService.filterByRatingRange(
                avgRatingMin != null ? avgRatingMin : 0.0,
                avgRatingMax != null ? avgRatingMax : 5.0,
                page
        );
        return new ResponseEntity<>(searchedDtos, HttpStatus.OK);
    }

    @Operation(summary = "get company by admin id", description = "get company by admin id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "company returned successfully"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasAnyRole('COMPADMIN', 'SYSADMIN')")
    @GetMapping(value = "/admin/{adminId}")
    public ResponseEntity<CompanyDto> getCompanyByAdminId(@PathVariable Integer adminId){
        CompanyDto company = companyService.findCompanyByAdminId(adminId);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }
}
