package com.isat46.isaback.controller;

import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;


@Controller
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "get page of all users", description = "get page of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "page of users returned successfully")
    })
    @GetMapping(value = "/all")
    public ResponseEntity<Page<UserDto>> getUsersPage(Pageable page){
        Page<UserDto> usersPage = userService.findAllPaged(page);
        return new ResponseEntity<>(usersPage, HttpStatus.OK);
    }

}
