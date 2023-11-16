package com.ufba.stock_control.entities;

public enum PaymentType {
    CREDIT_CARD(0),
    DEBIT_CARD(1),
    CASH(2);

    private Integer type;

    PaymentType(Integer type) {
      this.type =  type;
    }

    public Integer getType() {
      return this.type;
    }
}
