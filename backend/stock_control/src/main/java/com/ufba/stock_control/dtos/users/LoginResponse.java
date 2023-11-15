package com.ufba.stock_control.dtos.users;

import java.util.UUID;

import com.ufba.stock_control.entities.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
  String message;
}
