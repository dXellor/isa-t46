package com.isat46.isaback.controller;

import com.isat46.isaback.dto.inventory.InventoryItemDto;
import com.isat46.isaback.service.InventoryService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
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
}
