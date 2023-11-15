package com.ufba.stock_control.entities;

public enum UserRole {

  ADMIN("admin"),
  DEFAULT("default");

  private String role;
  
  UserRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return this.role;
  }
}
