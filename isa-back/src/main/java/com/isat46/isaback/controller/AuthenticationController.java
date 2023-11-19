package com.isat46.isaback.controller;

import com.isat46.isaback.dto.auth.JwtDto;
import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.dto.user.UserLoginDto;
import com.isat46.isaback.dto.user.UserRegistrationDto;
import com.isat46.isaback.model.User;
import com.isat46.isaback.service.AuthenticationService;
import com.isat46.isaback.util.TokenUtils;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    @Operation(summary = "user login", description = "user login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user logged in successfully",
                    content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PostMapping("/login")
    public ResponseEntity<JwtDto> createAuthenticationToken(@RequestBody UserLoginDto loginDto, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        long expiresIn = tokenUtils.getExpiredIn();

        return new ResponseEntity<>(new JwtDto(jwt, expiresIn), HttpStatus.OK);
    }
}
