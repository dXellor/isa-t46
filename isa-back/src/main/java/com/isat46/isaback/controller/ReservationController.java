package com.isat46.isaback.controller;

import com.isat46.isaback.dto.reservation.ReservationDto;
import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.dto.user.UserRegistrationDto;
import com.isat46.isaback.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(value = "api/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Operation(summary = "creates new reservation (company admin only)", description = "creates new reservation (company admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "reservation created successfully",
                    content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasRole('COMPADMIN')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDto> createNewReservation(@Parameter(required = true) @Valid @RequestBody ReservationDto reservationDto,
                                                               Principal user){
        ReservationDto newReservation = reservationService.createNewReservation(reservationDto, user.getName());
        if(newReservation != null)
            return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
