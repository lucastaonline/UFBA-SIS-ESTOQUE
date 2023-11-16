package com.ufba.stock_control.entities;

import java.util.UUID;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @ManyToOne()
  @JoinColumn(name =  "transaction_type_id", referencedColumnName = "id")
  private TransactionType transactionType;
  @ManyToOne()
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
  @OneToMany()
  @JoinColumn(name = "product_order_id", referencedColumnName = "id")
  private List<ProductOrder> productOrders;
  @Column
  private Double value = 0.0;
  @CreatedDate
  @Column(name = "created_at")
  private Date createdAt = new Date();
  @LastModifiedDate
  @Column(name = "updated_at")
  private Date updateAt =  new Date();
}
