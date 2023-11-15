package com.ufba.stock_control.entities;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
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
    @Enumerated(EnumType.STRING)
    private ProductCategory categories;
}
