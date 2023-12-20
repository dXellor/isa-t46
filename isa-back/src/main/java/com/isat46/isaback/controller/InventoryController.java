package com.isat46.isaback.controller;

import com.isat46.isaback.dto.company.CompanyRegistrationDto;
import com.isat46.isaback.dto.inventory.InventoryItemDto;
import com.isat46.isaback.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @Operation(summary = "get inventory page by company id", description = "get inventory page by company id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "inventory page returned successfully"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasAnyRole('USER', 'COMPADMIN', 'SYSADMIN')")
    @GetMapping(value = "/company/{companyId}")
    public ResponseEntity<Page<InventoryItemDto>> getInventoryByCompanyId(@PathVariable Integer companyId, Pageable page){
        Page<InventoryItemDto> items = inventoryService.findByCompanyIdPaged(companyId, page);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @Operation(summary = "add inventory item", description = "add inventory item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "inventory item created successfully"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasAnyRole('COMPADMIN')")
    @PostMapping(value = "")
    public ResponseEntity<InventoryItemDto> addInventoryItem(@Parameter(required = true) @Valid @RequestBody InventoryItemDto inventoryItemDto){
        InventoryItemDto item = inventoryService.addInventoryItem(inventoryItemDto);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @Operation(summary = "get inventory page by company id", description = "get inventory page by company id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "inventory page returned successfully"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasAnyRole('COMPADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeInventoryItem(@PathVariable Integer id){
        inventoryService.removeInventoryItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "update inventory item", description = "update inventory item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "inventory item updated successfully"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasAnyRole('COMPADMIN')")
    @PutMapping(value = "")
    public ResponseEntity<InventoryItemDto> updateInventoryItem(@Valid @RequestBody InventoryItemDto inventoryItemDto){
        InventoryItemDto updatedInventoryItem = inventoryService.updateInventoryItem(inventoryItemDto);
        return new ResponseEntity<>(updatedInventoryItem, HttpStatus.OK);
    }

}
