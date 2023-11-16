package com.ufba.stock_control.dtos.transactions;

import java.util.UUID;
import java.util.List;

import com.ufba.stock_control.entities.ProductOrder;

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
  List<ProductOrder> productOrders;
  Double finalValue;

}
