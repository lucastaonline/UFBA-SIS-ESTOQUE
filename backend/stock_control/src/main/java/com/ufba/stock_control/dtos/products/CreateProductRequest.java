package com.ufba.stock_control.dtos.products;

import com.ufba.stock_control.entities.ProductCategory;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
  
  @NotBlank
  String productName;
  @NotBlank
  Double price;
  @Nullable
  String description;
  @NotBlank
  Integer stock;
  @NotBlank
  ProductCategory category;
}
