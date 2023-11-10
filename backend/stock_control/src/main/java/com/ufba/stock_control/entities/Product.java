package com.ufba.stock_control.entities;

import java.util.UUID;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity(name = "products")
public @Data class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String name;
    @Column
    private Integer stock;
    @Column
    private Double price;
    @Column
    private String description;
    @ManyToMany
    @JoinTable(
      name =  "product_has_category",
      joinColumns = @JoinColumn(name =  "product_id"),
      inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<ProductCategory> categories;
}
