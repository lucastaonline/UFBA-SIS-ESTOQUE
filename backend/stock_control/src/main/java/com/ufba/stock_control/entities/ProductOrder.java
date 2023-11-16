package com.ufba.stock_control.entities;


import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
  @ManyToOne()
  @JoinColumn(name = "product_id")
  private Product product;
  @Column
  private Integer quantity;
  @Column
  private Double value;
  @JsonManagedReference
  @ManyToOne
  @JoinColumn(name = "transaction_id")
  private Transaction transaction;
}
