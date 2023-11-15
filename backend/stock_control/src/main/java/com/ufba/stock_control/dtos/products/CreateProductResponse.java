package com.ufba.stock_control.dtos.products;

import com.ufba.stock_control.entities.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductResponse {

  UUID id;
  String productName;
  Double price;
  String description;
  Integer stock;
  ProductCategory category;
}
