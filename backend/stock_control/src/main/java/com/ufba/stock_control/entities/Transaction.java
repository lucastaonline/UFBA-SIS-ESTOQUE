package com.ufba.stock_control.entities;

import java.util.UUID;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Enumerated(EnumType.STRING)
  private TransactionType type;
  @Enumerated(EnumType.STRING)
  private TransactionStatus status;
  @OneToOne()
  @JoinColumn(name = "seller_id", referencedColumnName = "id")
  private User seller;
  @OneToOne()
  @JoinColumn(name = "buyer_id", referencedColumnName = "id")
  private User buyer;
  @CreatedDate
  @Column(name = "created_at")
  private Date createdAt;
  @LastModifiedDate
  @Column(name = "updated_at")
  private Date updateAt;
}
