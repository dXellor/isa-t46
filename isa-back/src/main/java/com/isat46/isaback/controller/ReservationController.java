package com.isat46.isaback.controller;

import com.google.zxing.WriterException;
import com.isat46.isaback.dto.company.CompanyDto;
import com.isat46.isaback.dto.equipment.EquipmentDto;
import com.isat46.isaback.dto.reservation.*;
import com.isat46.isaback.model.Equipment;
import com.isat46.isaback.model.Reservation;
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
import java.io.IOException;
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

    @Operation(summary = "cancels reservation", description = "cancels reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservation canceled successfully",
                    content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/cancel/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDto> cancelReservation(@PathVariable Integer reservationId, Principal user){
        ReservationDto canceledReservation = reservationService.cancelReservation(reservationId, user.getName());
        if(canceledReservation != null)
            return new ResponseEntity<>(canceledReservation, HttpStatus.OK);
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

    @Operation(summary = "start location tracking", description = "start location tracking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "starting location tracking")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/tracking/{reservationId}")
    public ResponseEntity startTracking(@PathVariable Integer reservationId, Principal user){
        reservationService.startTracking(reservationId, user.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "get completed reservations for user", description = "get completed reservations for logged user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations returned sucessfully")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/completedReservations")
    public ResponseEntity<List<ReservationDto>> getCompletedReservationsForUser(Principal user){
        List<ReservationDto> reservations = reservationService.getCompletedReservationsForUser(user.getName());
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(summary = "get completed reservations for user sorted", description = "get completed reservations for logged user sorted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations returned successfully")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/completedReservationsSort")
    public ResponseEntity<List<ReservationDto>> sortReservationsByDurationAndDate(Principal user, @RequestParam(required = false) String duration, @RequestParam(required = false) String date){
        List<ReservationDto> reservations = reservationService.sortReservationsByDurationAndDate(user.getName(), duration, date);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
    
    @Operation(summary = "confirm reservation as a employee", description = "confirm reservation as a employee")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "reservation confirmed successfully")})
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/confirm/{reservationId}")
    public ResponseEntity<ReservationDto> confirmReservation(@PathVariable Integer reservationId, Principal user){
        ReservationDto reservation = reservationService.confirmReservation(reservationId, user.getName());
        if (reservation != null) {
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "deliver reservation equipment as a company admin", description = "deliver reservation equipment as a company admin")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "reservation delivered successfully")})
    @PreAuthorize("hasRole('COMPADMIN')")
    @GetMapping(value = "/deliver/{reservationId}")
    public ResponseEntity<ReservationDto> deliverEquipment(@PathVariable Integer reservationId){
        ReservationDto reservation = reservationService.deliverEquipment(reservationId);
        if (reservation != null) {
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "get confirmed reservations for company admin", description = "get confirmed reservations for company admin")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "reservations loaded")})
    @PreAuthorize("hasRole('COMPADMIN')")
    @GetMapping(value = "/get-confirmed")
    public ResponseEntity<Page<ReservationDto>> deliverEquipment(Principal user, Pageable page){
        Page<ReservationDto> reservations = reservationService.findConfirmedReservationsByAdmin(user.getName(), page);
        if (reservations != null) {
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "get qr code for reservation", description = "get qr code for reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qr code generated sucessfully")
    })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/generateQRCode")
    public ResponseEntity<ReservationQRCodeDto> generateQRCodeForReservation(
            @RequestBody ReservationDto reservationDto,
            @RequestParam int width,
            @RequestParam int height) {
        try {
            ReservationQRCodeDto qrCodeDto = reservationService.generateQRCodeForReservation(reservationDto, width, height);
            return ResponseEntity.ok(qrCodeDto);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "get pending reservations for user", description = "get completed pending for logged user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations returned sucessfully")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/pendingReservations")
    public ResponseEntity<List<ReservationDto>> getPendingReservationsForUser(Principal user){
        List<ReservationDto> reservations = reservationService.getPendingReservationsForUser(user.getName());
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(summary = "get cancelled reservations for user", description = "get cancelled reservations for logged user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations returned sucessfully")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/cancelledReservations")
    public ResponseEntity<List<ReservationDto>> getCancelledReservationsForUser(Principal user){
        List<ReservationDto> reservations = reservationService.getCancelledReservationsForUser(user.getName());
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(summary = "get confirmed reservations for user", description = "get confirmed reservations for logged user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations returned sucessfully")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/confirmedReservations")
    public ResponseEntity<List<ReservationDto>> getConfirmedReservationsForUser(Principal user){
        List<ReservationDto> reservations = reservationService.getConfirmedReservationsForUser(user.getName());
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

}
