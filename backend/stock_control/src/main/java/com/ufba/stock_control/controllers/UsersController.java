package com.ufba.stock_control.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufba.stock_control.dtos.users.CreateUserRequest;
import com.ufba.stock_control.dtos.users.CreateUserResponse;
import com.ufba.stock_control.dtos.users.LoginResponse;
import com.ufba.stock_control.dtos.users.LoginUserRequest;
import com.ufba.stock_control.entities.User;
import com.ufba.stock_control.services.JwtTokenService;
import com.ufba.stock_control.services.UsersService;

import jakarta.validation.Valid;

@RestController()
@RequestMapping(
  path = "/auth",
  consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE
)
public class UsersController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenService jwtTokenService;

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginUserRequest userDTO) {
      var userNamePasswordAuth = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
      var auth = authenticationManager.authenticate(userNamePasswordAuth);
      var token = jwtTokenService.generateToken((User) auth.getPrincipal());
      return ResponseEntity.status(HttpStatus.OK).body(
        LoginResponse
          .builder()
          .message("Usuário autenticado com sucesso")
          .token(token)
          .build());
    }

    @PostMapping("/create-user")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
      return ResponseEntity.status(HttpStatus.CREATED).body(usersService.createUser(createUserRequest));
    }

}