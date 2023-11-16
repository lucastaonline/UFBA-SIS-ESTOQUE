package com.ufba.stock_control.entities;

public enum TransactionDirection {

  INLET(0),
  OUTLET(1);

  private Integer direction;
  TransactionDirection(Integer direction) {
    this.direction = direction;
  }

  public Integer getDirection() {
    return this.direction;
  }
}
