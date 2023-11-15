package com.ufba.stock_control.dtos.users;

import com.ufba.stock_control.entities.UserRole;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
  @NotBlank
  String username;
  @NotBlank
  String email;
  @NotBlank
  UserRole role;
  @NotBlank
  String password;
  @Nullable
  String phoneNumber;
}
