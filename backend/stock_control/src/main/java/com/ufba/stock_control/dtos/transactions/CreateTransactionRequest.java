package com.ufba.stock_control.dtos.transactions;

import java.util.List;

import com.ufba.stock_control.entities.TransactionDirection;
import com.ufba.stock_control.entities.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {

  @NotNull
  UUID transactionTypeId;
  @NotNull
  UUID userId;
  @NotNull
  List<CreateTransactionItemRequest> items;
}
