package com.ufba.stock_control.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ufba.stock_control.entities.Product;
import com.ufba.stock_control.services.ProductsService;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Tag(name =  "Products", description = "Products related endpoints")
@RestController
@RequestMapping(
  path = "/products",
  consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProductsController {
  @Autowired
  private ProductsService productsService;

  @PostMapping
  public ResponseEntity<Product> addProduct(@RequestBody Product product) {
    return ResponseEntity.status(HttpStatus.OK).body(productsService.createProduct(product));
  }
  @GetMapping("/{id}")
  public ResponseEntity<Product> findProduct(@PathVariable("id") UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(productsService.findProduct(id));
  }
  @GetMapping
  public ResponseEntity<List<Product>> findAllProducts() {
    return ResponseEntity.status(HttpStatus.OK).body(productsService.listProducts());
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable("id") UUID id) {
    productsService.deleteProduct(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
  

}