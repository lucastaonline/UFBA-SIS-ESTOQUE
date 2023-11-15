package com.ufba.stock_control.dtos.transactions;

import com.ufba.stock_control.entities.TransactionStatus;
import com.ufba.stock_control.entities.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionResponse {
  
  UUID id;
  TransactionType type;
  TransactionStatus status;
  UUID SellerId; 
  UUID buyerId;
  Date createdAt;
  Date updatedAt;
  
}
