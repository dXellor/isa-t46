package com.isat46.isaback.controller;

import com.isat46.isaback.dto.reservation.AppointmentCreationDto;
import com.isat46.isaback.dto.reservation.ReservationCreationDto;
import com.isat46.isaback.dto.reservation.ReservationDto;
import com.isat46.isaback.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import java.security.Principal;

@Controller
@RequestMapping(value = "api/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Operation(summary = "get page of all reservations", description = "get page of all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "page of reservations returned successfully")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/all")
    public ResponseEntity<Page<ReservationDto>> getReservationsPage(Pageable page){
        Page<ReservationDto> reservationsPage = reservationService.findAllPaged(page);
        return new ResponseEntity<>(reservationsPage, HttpStatus.OK);
    }

    @Operation(summary = "creates new appointment (company admin only)", description = "creates new appointment (company admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "appointment created successfully",
                    content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentCreationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasRole('COMPADMIN')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDto> createNewAppointment(@Parameter(required = true) @Valid @RequestBody AppointmentCreationDto appointmentCreationDto){
        ReservationDto newReservation = reservationService.createNewAppointment(appointmentCreationDto);
        if(newReservation != null)
            return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "creates reservation with items (employee only, predefined appointment)", description = "creates reservation with items (employee only, predefined appointment)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservation updated successfully",
                    content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDto> createReservationWithPredefinedAppointment(@Parameter(required = true) @Valid @RequestBody ReservationCreationDto reservationCreationDto,
                                                               Principal user){
        ReservationDto newReservation = reservationService.createReservationWithPredefinedAppointment(reservationCreationDto, user.getName());
        if(newReservation != null)
            return new ResponseEntity<>(newReservation, HttpStatus.OK);
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
