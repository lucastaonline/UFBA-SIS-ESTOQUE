package com.ufba.stock_control.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufba.stock_control.entities.ProductOrder;

@Repository
public interface ProductsOrderRepository extends JpaRepository<ProductOrder, UUID> {
  
}
