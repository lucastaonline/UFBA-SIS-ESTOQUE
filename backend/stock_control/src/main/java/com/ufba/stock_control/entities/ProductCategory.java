package com.ufba.stock_control.entities;

public enum ProductCategory {

  MEDICAMENTOS("medicamentos"),
  VITAMINAS("vitaminas"),
  SUPLEMENTOS("suplementos"),
  HIGIENE("higiene"),
  COSMETICOS("cosmeticos");

  private String category;

  ProductCategory(String category) {
    this.category = category;
  }

  public String getCategory() {
    return this.category;
  }
  
}

