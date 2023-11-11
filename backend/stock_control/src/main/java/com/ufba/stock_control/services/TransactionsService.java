package com.ufba.stock_control.services;

import com.ufba.stock_control.entities.Product;
import com.ufba.stock_control.entities.Transaction;
import com.ufba.stock_control.repositories.ProductsRepository;
import com.ufba.stock_control.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

// TODO: FINISH THE SERVICES
@Service
public class TransactionsService {
  @Autowired
  private TransactionsRepository transactionsRepository; 

  public Transaction createTransaction(Transaction transaction) {
    return transactionsRepository.save(transaction);
  }

  public Transaction findTransaction(UUID id) {
    return transactionsRepository.findById(id).orElseThrow(()-> new RuntimeException());
  }

  public void deleteTransaction(UUID id) {
    transactionsRepository.deleteById(id);
  }

  public Product updateTransaction() {

    return null;
  }

  public List<Transaction> listTransactions() {
    return transactionsRepository.findAll();
  }

}
