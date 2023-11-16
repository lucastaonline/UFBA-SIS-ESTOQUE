package com.ufba.stock_control.entities;
public enum TransactionDescription {

  BUY(0),
  SELL(1),
  CUSTOMER_CHARGEBACK(2),
  DISTRIBUTOR_CHARGEBACK(3),
  DISCARD(4);


  private Integer direction;
  TransactionDescription(Integer direction) {
    this.direction = direction;
  }

  public Integer getDirection() {
    return this.direction;
  }
}
