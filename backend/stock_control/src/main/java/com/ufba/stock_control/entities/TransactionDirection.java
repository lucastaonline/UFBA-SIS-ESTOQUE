package com.ufba.stock_control.entities;
/*
 * compra - entrada
venda - saida
descarte - saida
extorno cliente - entrada
extorno distribuidor - saida
 * 
 */
public enum TransactionDirection {

  BUY("buy"),
  SELL("sell"),
  CUSTOMER_CHARGEBACK("customer_chargeback"),
  DISTRIBUTOR_CHARGEBACK("distributor_chargeback");


  private String direction;
  TransactionDirection(String direction) {
    this.direction = direction;
  }

  public String getDirection() {
    return this.direction;
  }
}
