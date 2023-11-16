package com.ufba.stock_control.dtos.transactions;

import java.util.List;

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
  List<CreateTransactionItemRequest> items;
}
