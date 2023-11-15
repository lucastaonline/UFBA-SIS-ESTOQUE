package com.ufba.stock_control.services;

import com.ufba.stock_control.dtos.products.CreateProductRequest;
import com.ufba.stock_control.dtos.transactions.CreateTransactionRequest;
import com.ufba.stock_control.dtos.transactions.CreateTransactionResponse;
import com.ufba.stock_control.entities.Product;
import com.ufba.stock_control.entities.Transaction;
import com.ufba.stock_control.helpers.mappers.ProductsMapper;
import com.ufba.stock_control.helpers.mappers.TransactionMapper;
import com.ufba.stock_control.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

// TODO: FINISH THE SERVICES
@Service
public class TransactionsService {

  private final TransactionMapper transactionMapper;
  @Autowired
  private TransactionsRepository transactionsRepository;

  public TransactionsService(
    TransactionMapper transactionMapper
  ) {
    this.transactionMapper = transactionMapper;
  }
  

  public CreateTransactionResponse createTransaction(CreateTransactionRequest createTransactionRequest) {
    Transaction createdTransaction = this.transactionsRepository.save(transactionMapper.toTransactionEntity(createTransactionRequest));
    return CreateTransactionResponse.builder()
      .id(createdTransaction.getId())
      .type(createdTransaction.getType())
      .status(createdTransaction.getStatus())
      .SellerId(createdTransaction.getSeller().getId())
      .buyerId(createdTransaction.getBuyer().getId())
      .build();
  }

  public Transaction findTransaction(UUID id) {
    return transactionsRepository.findById(id).orElseThrow(()-> new RuntimeException());
  }

  public void deleteTransaction(UUID id) {
    transactionsRepository.deleteById(id);
  }

  public Transaction updateTransaction(UUID id, Transaction createTransactionRequest) {
    Transaction existingTransaction = transactionsRepository.findById(id).orElseThrow(()-> new RuntimeException());
    existingTransaction.setType(createTransactionRequest.getType());
    existingTransaction.setStatus(createTransactionRequest.getStatus());
    existingTransaction.setSeller(createTransactionRequest.getSeller());
    existingTransaction.setBuyer(createTransactionRequest.getBuyer());
    return transactionsRepository.save(existingTransaction);
  }

  public List<Transaction> listTransactions() {
    return transactionsRepository.findAll();
  }

}
