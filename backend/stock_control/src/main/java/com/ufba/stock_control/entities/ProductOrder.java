package com.ufba.stock_control.entities;


import java.util.UUID;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "product_order")
public class ProductOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @OneToMany(mappedBy = "productOrder")
  private List<Product> product;
  @Column
  private Integer quantity;
  @Column
  private Double value;
  @ManyToOne
  @JoinColumn(name = "transaction_id")
  private Transaction transaction;
}
