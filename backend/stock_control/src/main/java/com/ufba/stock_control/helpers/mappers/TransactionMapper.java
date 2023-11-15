package com.ufba.stock_control.helpers.mappers;

import com.ufba.stock_control.dtos.transactions.CreateTransactionRequest;
import com.ufba.stock_control.entities.Transaction;
import com.ufba.stock_control.repositories.ProductsRepository;
import com.ufba.stock_control.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
  @Autowired
  private UsersRepository usersRepository;
  public Transaction toTransactionEntity(CreateTransactionRequest createdTransaction) {
    Transaction transaction = new Transaction();
    transaction.setType(createdTransaction.getType());
    transaction.setStatus(createdTransaction.getStatus());
    transaction.setSeller(usersRepository.findOneById(createdTransaction.getSellerId()));
    transaction.setBuyer(usersRepository.findOneById(createdTransaction.getBuyerId()));
    return transaction;
  }
  
}
