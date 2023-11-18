package com.isat46.isaback.controller;

import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.dto.user.UserRegistrationDto;
import com.isat46.isaback.service.AuthenticationService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@Controller
@RequestMapping(value = "api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "creates new user", description = "creates new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user created successfully",
                    content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> registerNewUser(@Parameter(required = true) @Valid @RequestBody UserRegistrationDto userRegistrationDto){
        UserDto newUser = authenticationService.registerUser(userRegistrationDto);
        if(newUser != null)
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "verifies new user", description = "verifies new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user verified successfully",
                    content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @GetMapping(value = "/verify/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> verifyUser(@Parameter(required = true) @PathVariable String token){
        UserDto newUser = authenticationService.verify(token);
        if(newUser != null)
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
