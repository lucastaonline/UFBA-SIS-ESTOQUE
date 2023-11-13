package com.ufba.stock_control.services;

import  java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.ufba.stock_control.dtos.products.CreateProductDTO;
import com.ufba.stock_control.entities.Product;
import com.ufba.stock_control.repositories.ProductsRepository;
import org.springframework.stereotype.Service;

// TODO: FINISH THE SERVICES
@Service
public class ProductsService {
  @Autowired
  private ProductsRepository productsRepository; 

  public Product createProduct(CreateProductDTO productDTO) {
    Product product = new Product(productDTO);
    return productsRepository.save(product);
  }

  public Product findProduct(UUID id) {
    return productsRepository.findById(id).orElseThrow(()-> new RuntimeException());
  }

  public void deleteProduct(UUID id) {
     productsRepository.deleteById(id);
  }

  public Product updateProduct(UUID id, Product requestProduct) {
    Product existingProduct = productsRepository.findById(id).orElseThrow(()-> new RuntimeException());
    existingProduct.setName(requestProduct.getName());
    existingProduct.setDescription(requestProduct.getDescription());
    existingProduct.setPrice(requestProduct.getPrice());
    existingProduct.setStock(requestProduct.getStock()); 
    existingProduct.setCategories(requestProduct.getCategories());
    return productsRepository.save(existingProduct);
  }

  public List<Product> listProducts() {
    return productsRepository.findAll();
  }

}
