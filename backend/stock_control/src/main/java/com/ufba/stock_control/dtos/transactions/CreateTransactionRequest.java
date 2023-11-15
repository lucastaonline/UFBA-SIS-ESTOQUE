package com.ufba.stock_control.dtos.transactions;


import com.ufba.stock_control.entities.ProductCategory;
import com.ufba.stock_control.entities.TransactionStatus;
import com.ufba.stock_control.entities.TransactionType;
import io.micrometer.common.lang.Nullable;
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
  TransactionType type;
  @NotNull
  TransactionStatus status;
  @NotNull
  UUID SellerId;
  @NotNull
  UUID buyerId;
}
