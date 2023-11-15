package com.ufba.stock_control.services;

import com.ufba.stock_control.dtos.products.CreateProductRequest;
import com.ufba.stock_control.dtos.transactions.CreateTransactionRequest;
import com.ufba.stock_control.dtos.transactions.CreateTransactionResponse;
import com.ufba.stock_control.entities.Product;
import com.ufba.stock_control.entities.Transaction;
import com.ufba.stock_control.helpers.mappers.ProductsMapper;
import com.ufba.stock_control.helpers.mappers.TransactionMapper;
import com.ufba.stock_control.repositories.TransactionsRepository;
import com.ufba.stock_control.repositories.UsersRepository;
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
  
  @Autowired
  private UsersRepository usersRepository;

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

  public CreateTransactionResponse findTransaction(UUID id) {
    Transaction createdTransaction = transactionsRepository.findById(id).orElseThrow(()-> new RuntimeException());
    return CreateTransactionResponse.builder()
      .id(createdTransaction.getId())
      .type(createdTransaction.getType())
      .status(createdTransaction.getStatus())
      .SellerId(createdTransaction.getSeller().getId())
      .buyerId(createdTransaction.getBuyer().getId())
      .build();
  }

  public void deleteTransaction(UUID id) {
    transactionsRepository.deleteById(id);
  }

  public CreateTransactionResponse updateTransaction(UUID id, CreateTransactionRequest createTransactionRequest) {
    Transaction createdTransaction = this.transactionsRepository.save(transactionMapper.toTransactionEntity(createTransactionRequest));
    return CreateTransactionResponse.builder()
      .id(createdTransaction.getId())
      .type(createdTransaction.getType())
      .status(createdTransaction.getStatus())
      .SellerId(createdTransaction.getSeller().getId())
      .buyerId(createdTransaction.getBuyer().getId())
      .build();
  }

  public List<Transaction> listTransactions() {
    return transactionsRepository.findAll();
  }

}
