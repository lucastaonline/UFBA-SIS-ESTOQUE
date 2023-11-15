package com.ufba.stock_control.helpers.mappers;


import org.springframework.stereotype.Component;

import com.ufba.stock_control.dtos.users.CreateUserRequest;
import com.ufba.stock_control.entities.User;

@Component
public class UsersMapper {
  
  public User toUserEntity(CreateUserRequest createdUser) {
    User user = new User();
    user.setEmail(createdUser.getEmail());
    user.setUserName(createdUser.getUsername());
    user.setPassword(createdUser.getPassword());
    user.setPhoneNumber(createdUser.getPhoneNumber());
    user.setRole(createdUser.getRole());
    return user;
  }
}
