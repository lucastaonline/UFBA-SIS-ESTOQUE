package com.ufba.stock_control.dtos.transactions;

import java.util.UUID;
import java.util.List;
import java.util.Date;

import com.ufba.stock_control.entities.ProductOrder;
import com.ufba.stock_control.entities.TransactionDescription;
import com.ufba.stock_control.entities.TransactionDirection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatedTransactionResponse {
  UUID transactionId;
  TransactionDescription transactionTypeDescription;
  TransactionDirection transactionTypeDirection;
  List<ProductOrder> productOrders;
  Double finalValue;
  Date createdAt;
}
