package com.ufba.stock_control.helpers.mappers;

import com.ufba.stock_control.dtos.transactions.CreateTransactionRequest;
import com.ufba.stock_control.entities.Transaction;
import com.ufba.stock_control.repositories.TransactionTypeRepository;
import com.ufba.stock_control.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

  @Autowired
  private UsersRepository usersRepository;
  @Autowired
  private TransactionTypeRepository transactionTypeRepository;

  public Transaction toTransactionEntity(CreateTransactionRequest createdTransaction) {
    Transaction transaction = new Transaction();
    transaction.setTransactionType(transactionTypeRepository.findOneById(createdTransaction.getTransactionTypeId()));
    transaction.setUser(usersRepository.findOneById(createdTransaction.getUserId()));
    return transaction;
  }

}
