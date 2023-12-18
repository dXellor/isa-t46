package com.isat46.isaback.controller;

import com.isat46.isaback.dto.reservation.ReservationItemDto;
import com.isat46.isaback.service.ReservationItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/reservation-item")
public class ReservationItemController {

    @Autowired
    ReservationItemService reservationItemService;

    @Operation(summary = "get reservation items for specific reservation", description = "get reservation items for specific reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "items returned successfully"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasAnyRole('USER', 'COMPADMIN', 'SYSADMIN')")
    @GetMapping(value = "/reservation/{reservationId}")
    public ResponseEntity<List<ReservationItemDto>> getReservationItemsByReservationId(@PathVariable Integer reservationId){
        List<ReservationItemDto> items = reservationItemService.findReservationItemsByReservationId(reservationId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
