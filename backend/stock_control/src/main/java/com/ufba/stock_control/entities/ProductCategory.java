package com.ufba.stock_control.entities;

public enum ProductCategory {

  MEDICAMENTOS(0),
  VITAMINAS(1),
  SUPLEMENTOS(2),
  HIGIENE(3),
  COSMETICOS(4);

  private Integer category;

  ProductCategory(Integer category) {
    this.category = category;
  }

  public Integer getCategory() {
    return this.category;
  }
  
}

