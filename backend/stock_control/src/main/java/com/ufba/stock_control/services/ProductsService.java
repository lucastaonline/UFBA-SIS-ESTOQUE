package com.ufba.stock_control.services;

import  java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.ufba.stock_control.dtos.products.CreateProductDTO;
import com.ufba.stock_control.entities.Product;
import com.ufba.stock_control.repositories.ProductsRepository;
// TODO: FINISH THE SERVICES
public class ProductsService {
  @Autowired
  private final ProductsRepository productsRepository; 

  public Product createProduct(CreateProductDTO data) {
    try {
      return this.productsRepository.;
    } catch(Error error) {
      System.out.println(error.toString());
    }
  }

  public Product findProduct() {

  }

  public Product deleteProduct() {

  }

  public Product updateProduct() {

  }

  public List<Product> listProducts() {

  }

}
