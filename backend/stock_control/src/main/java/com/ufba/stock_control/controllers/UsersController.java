package com.ufba.stock_control.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufba.stock_control.dtos.users.UserLoginDTO;
import com.ufba.stock_control.services.JwtTokenService;
import com.ufba.stock_control.services.UsersService;

@RestController()
@RequestMapping("/auth")
public class UsersController {

    private final JwtTokenService jwtTokenService;
    private final UsersService usersService;

    public UsersController(JwtTokenService jwtTokenService, UsersService usersService) {
        this.jwtTokenService = jwtTokenService;
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userDTO) {
        return usersService.login(userDTO)
                .map(userId -> {
                    String token = jwtTokenService.generateToken(userId, "USER");
                    return new ResponseEntity<>("Bearer " + token, HttpStatus.OK);
                }).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}