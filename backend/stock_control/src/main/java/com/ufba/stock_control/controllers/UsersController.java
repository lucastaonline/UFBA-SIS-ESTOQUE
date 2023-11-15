package com.ufba.stock_control.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufba.stock_control.dtos.users.CreateUserRequest;
import com.ufba.stock_control.dtos.users.CreateUserResponse;
import com.ufba.stock_control.dtos.users.LoginResponse;
import com.ufba.stock_control.dtos.users.LoginUserRequest;
import com.ufba.stock_control.services.UsersService;

@RestController()
@RequestMapping(
  path = "/auth",
  consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE
)
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserRequest userDTO) {
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(usersService.login(userDTO));
    }

    @PostMapping("/create-user")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
      return ResponseEntity.status(HttpStatus.CREATED).body(usersService.createUser(createUserRequest));
    }

}