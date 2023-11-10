package com.ufba.stock_control.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufba.stock_control.dtos.products.CreateProductDTO;
import com.ufba.stock_control.entities.Product;
import com.ufba.stock_control.services.ProductsService;

@RestController
@RequestMapping(
  path = "/products",
  consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProductsController {
  @Autowired
  private ProductsService productsService;

  @PostMapping()
  ResponseEntity<Product> createProduct(@RequestBody CreateProductDTO data) {
    return this.productsService.createProduct(data);
  }

}
