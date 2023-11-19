package com.isat46.isaback.controller;

import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.security.Principal;


@Controller
@RequestMapping(value = "api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "get page of all users", description = "get page of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "page of users returned successfully")
    })
    @PreAuthorize("hasRole('SYSADMIN')")
    @GetMapping(value = "/all")
    public ResponseEntity<Page<UserDto>> getUsersPage(Pageable page){
        Page<UserDto> usersPage = userService.findAllPaged(page);
        return new ResponseEntity<>(usersPage, HttpStatus.OK);
    }

    @Operation(summary = "get page of all company admins", description = "get page of all company admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "page of users returned successfully")
    })
    @PreAuthorize("hasRole('SYSADMIN')")
    @GetMapping(value = "/all/ca")
    public ResponseEntity<Page<UserDto>> getCompanyAdminsPage(Pageable page){
        Page<UserDto> companyAdminsPage = userService.findAllCompanyAdminsPaged(page);
        return new ResponseEntity<>(companyAdminsPage, HttpStatus.OK);
    }

    @Operation(summary = "get user from jwt", description = "get user from jwt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user returned successfully")
    })
    @PreAuthorize("hasAnyRole('USER', 'SYSADMIN', 'COMPADMIN')")
    @GetMapping(value = "")
    public ResponseEntity<UserDto> getUser(Principal user){
        UserDto userDto = userService.findByEmail(user.getName());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
