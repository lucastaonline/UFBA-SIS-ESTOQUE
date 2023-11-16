package com.ufba.stock_control.entities;
public enum TransactionDescription {

  BUY("buy"),
  SELL("sell"),
  CUSTOMER_CHARGEBACK("customer_chargeback"),
  DISTRIBUTOR_CHARGEBACK("distributor_chargeback"),
  DISCARD("discard");


  private String direction;
  TransactionDescription(String direction) {
    this.direction = direction;
  }

  public String getDirection() {
    return this.direction;
  }
}
