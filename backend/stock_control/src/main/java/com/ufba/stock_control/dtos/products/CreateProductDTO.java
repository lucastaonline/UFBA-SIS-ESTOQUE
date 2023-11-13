package com.ufba.stock_control.dtos.products;

import java.util.UUID;

import com.ufba.stock_control.entities.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public abstract class CreateProductDTO {
  ProductCategory category;
  String name;
  UUID selledId;
  Double price;
  String description;
  Integer stock;
}
