package com.isat46.isaback.controller;

import com.isat46.isaback.dto.address.AddressDto;
import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(value = "/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @Operation(summary = "get company address", description = "get company address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "address returned successfully")
    })
    @GetMapping(value = "/{companyId}")
    public ResponseEntity<AddressDto> getAddressBtCompanyId(@PathVariable Integer companyId){
        AddressDto addressDto = addressService.findAddressByCompanyId(companyId);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }


}
