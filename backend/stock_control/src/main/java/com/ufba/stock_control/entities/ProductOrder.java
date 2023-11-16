package com.ufba.stock_control.entities;


import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product_order")
public class ProductOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @OneToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;
  @Column
  private Integer quantity;
  @Column
  private Double value;
}
