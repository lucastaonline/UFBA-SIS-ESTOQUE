package com.ufba.stock_control.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufba.stock_control.dtos.users.UserLoginDTO;
import com.ufba.stock_control.entities.User;
import com.ufba.stock_control.repositories.UsersRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
  @Autowired
  private UsersRepository usersRepository;

  // Hard coded login setup for JWT showcase, do not use in a real application.
  public Optional<UUID> login(UserLoginDTO userDTO) {

    User foundUser = this.usersRepository.findOneByUserName(userDTO.login());

    if (userDTO.password() != null && foundUser.getPassword().equals(userDTO.password())) {
      return Optional.of(foundUser.getId());
    }

    return Optional.empty();
  }
}