package com.ufba.stock_control.dtos.products;

import com.ufba.stock_control.entities.ProductCategory;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class CreateProductRequest {
  
  @NotNull
  String productName;
  @NotNull
  Double price;
  @Nullable
  String description;
  @NotNull
  Integer stock;
  @NotNull
  ProductCategory category;
}
