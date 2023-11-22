package com.isat46.isaback.controller;

import com.isat46.isaback.dto.equipment.EquipmentDto;
import com.isat46.isaback.service.EquipmentService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Operation(summary = "get page of all equipment", description = "get page of all equipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "page of equipment returned successfully")
    })
    @GetMapping(value = "/all")
    public ResponseEntity<Page<EquipmentDto>> getAllEquipment(Pageable page){
        Page<EquipmentDto> equipmentPage = equipmentService.findAllPaged(page);
        return new ResponseEntity<>(equipmentPage, HttpStatus.OK);
    }


    @Operation(summary = "get page of equipment that belongs to the company", description = "get page of equipment that belongs to the company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "page of equipment returned successfully")
    })
    @GetMapping(value = "/company/{companyId}")
    public ResponseEntity<Page<EquipmentDto>> getEquipmentByCompanyId(@PathVariable Integer companyId, Pageable page){
        Page<EquipmentDto> equipmentPage = equipmentService.findEquipmentByCompanyId(companyId, page);
        return new ResponseEntity<>(equipmentPage, HttpStatus.OK);
    }

    @Operation(summary = "filter equipment", description = "filter equipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "page of filtered equipment returned successfully")
    })
    @PreAuthorize("hasAnyRole('USER', 'SYSADMIN', 'COMPADMIN')")
    @GetMapping(value = "")
    public ResponseEntity<Page<EquipmentDto>> getFilteredEquipment(@RequestParam(required = false, defaultValue = "") String name,
                                                                   @RequestParam(required = false, defaultValue = "") String type,
                                                                   @RequestParam(required = false, defaultValue = "0") double priceMin,
                                                                   @RequestParam(required = false, defaultValue = "1000000") double priceMax, Pageable page){
        Page<EquipmentDto> equipmentPage = equipmentService.filterEquipment(name, type, priceMin, priceMax, page);
        return new ResponseEntity<>(equipmentPage, HttpStatus.OK);
    }
}
