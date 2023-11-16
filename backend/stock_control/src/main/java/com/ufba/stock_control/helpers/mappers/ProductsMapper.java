package com.ufba.stock_control.helpers.mappers;

import com.ufba.stock_control.dtos.products.CreateProductRequest;
import com.ufba.stock_control.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductsMapper {

  public Product toProductEntity(CreateProductRequest createdProduct) {
    Product product = new Product();
    product.setName(createdProduct.getName());
    product.setStock(createdProduct.getStock());
    product.setPrice(createdProduct.getPrice());
    product.setDescription(createdProduct.getDescription());
    product.setCategories(createdProduct.getCategories());
    return product;
  }
  
}
