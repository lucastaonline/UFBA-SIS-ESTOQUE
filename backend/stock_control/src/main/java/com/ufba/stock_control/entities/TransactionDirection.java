package com.ufba.stock_control.entities;

public enum TransactionDirection {

  INLET("inlet"),
  OUTLET("outlet");

  private String direction;
  TransactionDirection(String direction) {
    this.direction = direction;
  }

  public String getDirection() {
    return this.direction;
  }
}
