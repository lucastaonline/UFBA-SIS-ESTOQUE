package com.ufba.stock_control.services;

import  java.util.List;
import java.util.UUID;

import com.ufba.stock_control.dtos.products.CreateProductResponse;
import com.ufba.stock_control.helpers.mappers.ProductsMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ufba.stock_control.dtos.products.CreateProductRequest;
import com.ufba.stock_control.entities.Product;
import com.ufba.stock_control.repositories.ProductsRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {
  private final ProductsMapper productsMapper;
  
  @Autowired
  private ProductsRepository productsRepository;

  public ProductsService( ProductsMapper productsMapper) {
    this.productsMapper = productsMapper;
  }

  public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
    Product createdProduct = this.productsRepository.save(productsMapper.toProductEntity(createProductRequest));
    return CreateProductResponse.builder()
      .id(createdProduct.getId())
      .name(createdProduct.getName())
      .description(createdProduct.getDescription())
      .price(createdProduct.getPrice())
      .stock(createdProduct.getStock())
      .categories(createdProduct.getCategories())
      .build();
    
  }

  public CreateProductResponse findProduct(UUID id) {
    Product createdProduct = productsRepository.findById(id).orElseThrow(()-> new RuntimeException());
    return CreateProductResponse.builder()
      .id(createdProduct.getId())
      .name(createdProduct.getName())
      .description(createdProduct.getDescription())
      .price(createdProduct.getPrice())
      .stock(createdProduct.getStock())
      .categories(createdProduct.getCategories())
      .build();
  }

  public void deleteProduct(UUID id) {
     productsRepository.deleteById(id);
  }

  public Product updateProduct(UUID id, CreateProductRequest requestProduct) {
    Product existingProduct = productsRepository.findById(id).orElseThrow(() -> new RuntimeException());
    existingProduct.setName(requestProduct.getProductName());
    existingProduct.setDescription(requestProduct.getDescription());
    existingProduct.setPrice(requestProduct.getPrice());
    existingProduct.setStock(requestProduct.getStock()); 
    existingProduct.setCategories(requestProduct.getCategory());
    return productsRepository.save(existingProduct);
  }

  public List<Product> listProducts() {
    return productsRepository.findAll();
  }
  
  public CreateProductResponse findProductByName(String name) {
    Product createdProduct = productsRepository.findProductByName(name);
    return CreateProductResponse.builder()
      .id(createdProduct.getId())
      .name(createdProduct.getName())
      .description(createdProduct.getDescription())
      .price(createdProduct.getPrice())
      .stock(createdProduct.getStock())
      .categories(createdProduct.getCategories())
      .build();
  }

}
