package com.ufba.stock_control.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufba.stock_control.entities.Product;

public interface ProductsRepository extends JpaRepository<Product, UUID> {
  Product findProductByName(String name);
  Product findOneById(UUID id);
  
}
