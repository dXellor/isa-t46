package com.isat46.isaback.controller;

import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.dto.equipment.EquipmentDto;
import com.isat46.isaback.dto.reservation.*;
import com.isat46.isaback.model.Equipment;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@CrossOrigin(origins = "*")
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

    @Operation(summary = "returns reservations by day", description = "returns reservations by day")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations returned successfully",
                    content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentCreationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasRole('COMPADMIN')")
    @GetMapping(value = "/day/{year}/{month}/{day}")
    public ResponseEntity<List<ReservationDto>> findByDay(Principal user, @PathVariable Integer year, @PathVariable Integer month, @PathVariable Integer day){
        List<ReservationDto> reservations = reservationService.findByDay(user.getName(), year, month, day);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(summary = "returns reservations by week", description = "returns reservations by week - passed date should be the first day of the week")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations returned successfully",
                    content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentCreationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasRole('COMPADMIN')")
    @GetMapping(value = "/week/{year}/{month}/{day}")
    public ResponseEntity<List<ReservationDto>> findByWeek(Principal user, @PathVariable Integer year, @PathVariable Integer month, @PathVariable Integer day){
        List<ReservationDto> reservations = reservationService.findByWeek(user.getName(), year, month, day);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(summary = "returns reservations by month and year", description = "returns reservations by month and year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations returned successfully",
                    content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentCreationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasRole('COMPADMIN')")
    @GetMapping(value = "/month/{year}/{month}")
    public ResponseEntity<List<ReservationDto>> findByMonthAndYear(Principal user, @PathVariable Integer year, @PathVariable Integer month){
        List<ReservationDto> reservations = reservationService.findByMonthAndYear(user.getName(), year, month);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(summary = "returns reservations by year", description = "returns reservations by year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations returned successfully",
                    content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentCreationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasRole('COMPADMIN')")
    @GetMapping(value = "/year/{year}")
    public ResponseEntity<List<ReservationDto>> findByYear(Principal user, @PathVariable Integer year){
        List<ReservationDto> reservations = reservationService.findByYear(user.getName(), year);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(summary = "find available time slots", description = "find available time slots (out of order appointments)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found successfully"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/availableTimeSlots")
    public ResponseEntity<List<ReservationDto>> offerOutOfOrderTimes(
            @RequestParam(required = false) int companyId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
            ) {
        List<ReservationDto> timeSlots = reservationService.offerOutOfOrderReservations(companyId, date);
        return new ResponseEntity<>(timeSlots, HttpStatus.OK);
    }

    @Operation(
            summary = "creates reservation with items (out of order appointments)", description = "creates reservation with items (out of order appointments)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservation added successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OutOfOrderReservationDto.class))}),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/outOfOrderCreate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ReservationDto> createReservationWithOutOfOrderAppointment(
            @RequestBody(required = false) OutOfOrderReservationDto outOfOrderReservationDto,
            Principal user) {

        ReservationDto newReservation = reservationService.createReservationWithOutOfOrderAppointment(outOfOrderReservationDto, user.getName());
        if (newReservation != null) {
            return new ResponseEntity<>(newReservation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "get reservations for employee", description = "get reservations for employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations returned sucessfully")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/employee")
    public ResponseEntity<Page<ReservationDto>> getReservationsForEmployee(Pageable page, Principal user){
        Page<ReservationDto> reservations = reservationService.findByEmployee(page, user.getName());
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(summary = "get reservations for company", description = "get reservations for company admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations returned sucessfully")
    })
    @PreAuthorize("hasRole('COMPADMIN')")
    @GetMapping(value = "/company")
    public ResponseEntity<Page<ReservationDto>> getReservationForAdmin(Pageable page){
        Page<ReservationDto> reservations = reservationService.findByCompanyAdmin(page);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(summary = "get available reservations for company", description = "get available reservations for company admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations returned sucessfully")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/available/{companyId}")
    public ResponseEntity<List<ReservationDto>> getAvailableCompanyAppointments(@PathVariable int companyId){
        List<ReservationDto> reservations = reservationService.findAvailableAppointmentsByCompany(companyId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
