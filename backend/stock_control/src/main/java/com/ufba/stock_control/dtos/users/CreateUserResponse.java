package com.ufba.stock_control.dtos.users;

import java.util.UUID;

import com.ufba.stock_control.entities.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@
Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
  UUID id;
  String username;
  String email;
  UserRole role;
}
