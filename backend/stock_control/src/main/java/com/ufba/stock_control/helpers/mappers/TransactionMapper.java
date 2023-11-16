package com.ufba.stock_control.helpers.mappers;

import com.ufba.stock_control.dtos.transactions.CreateTransactionRequest;
import com.ufba.stock_control.dtos.transactions.CreatedTransactionResponse;
import com.ufba.stock_control.entities.Transaction;
import com.ufba.stock_control.entities.User;
import com.ufba.stock_control.repositories.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

  @Autowired
  private TransactionTypeRepository transactionTypeRepository;

  public Transaction toTransactionEntity(CreateTransactionRequest createdTransaction, User user) {
    Transaction transaction = new Transaction();
    transaction.setTransactionType(transactionTypeRepository.findOneById(createdTransaction.getTransactionTypeId()));
    transaction.setUser(user);
    return transaction;
  }
  public CreatedTransactionResponse toTransactionTransacionResponse(Transaction transaction) {
    return CreatedTransactionResponse.builder()
      .transactionTypeDirection(transaction.getTransactionType().getDirection())
      .transactionTypeDescription(transaction.getTransactionType().getDescription())
      .transactionId(transaction.getId())
      .finalValue(transaction.getValue())
      .productOrders(transaction.getProductOrders())
      .createdAt(transaction.getCreatedAt())
      .build();
  }

}
