package com.ufba.stock_control.services;

import com.ufba.stock_control.dtos.transactions.CreateTransactionItemRequest;
import com.ufba.stock_control.dtos.transactions.CreateTransactionRequest;
import com.ufba.stock_control.dtos.transactions.CreateTransactionResponse;
import com.ufba.stock_control.entities.Product;
import com.ufba.stock_control.entities.ProductOrder;
import com.ufba.stock_control.entities.Transaction;
import com.ufba.stock_control.entities.TransactionType;
import com.ufba.stock_control.entities.User;
import com.ufba.stock_control.exceptions.ConflictException;
import com.ufba.stock_control.exceptions.NotFoundException;
import com.ufba.stock_control.helpers.mappers.TransactionMapper;
import com.ufba.stock_control.repositories.ProductsOrderRepository;
import com.ufba.stock_control.repositories.ProductsRepository;
import com.ufba.stock_control.repositories.TransactionTypeRepository;
import com.ufba.stock_control.repositories.TransactionsRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class TransactionsService {

  private final TransactionMapper transactionMapper;
  private final TransactionsRepository transactionsRepository;
  private final ProductsRepository productsRepository;
  private final TransactionTypeRepository transactionTypeRepository;
  private final ProductsOrderRepository productsOrderRepository;
 

  public TransactionsService(
      TransactionMapper transactionMapper,
      TransactionsRepository transactionsRepository,
      ProductsRepository productsRepository,
      TransactionTypeRepository transactionTypeRepository,
      ProductsOrderRepository productsOrderRepository
  ) {
    this.transactionMapper = transactionMapper;
    this.transactionsRepository = transactionsRepository;
    this.productsRepository = productsRepository;
    this.transactionTypeRepository = transactionTypeRepository;
    this.productsOrderRepository =  productsOrderRepository;
  }

  private User getLoggedUserDetails() {
    Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
    User userDetails =  (User) authentication.getPrincipal();
    return userDetails;
  }


  public CreateTransactionResponse createTransaction(CreateTransactionRequest createTransactionRequest) {
    Double transactionValue = 0.0;
    List<ProductOrder> productOrders = new ArrayList<>();
    
    for (CreateTransactionItemRequest item : createTransactionRequest.getItems()) {
      Double unitaryPrice;
      Product foundProduct = productsRepository.findOneById(item.productId());
      if (foundProduct == null) {
        throw new NotFoundException("Produto não encontrado");
      }

      if (foundProduct.getStock() < item.quantity()) {
        throw new ConflictException("Produto sem estoque no momento:" + foundProduct.getName());
      }

      unitaryPrice = foundProduct.getPrice() * item.quantity();

      foundProduct.setStock(foundProduct.getStock() - item.quantity());

      ProductOrder createdProductOrder = ProductOrder.builder()
        .product(foundProduct)
        .value(unitaryPrice)
        .quantity(item.quantity())
        .build();
      
      transactionValue += unitaryPrice;
      
      productOrders.add(createdProductOrder);
      this.productsRepository.save(foundProduct);
      this.productsOrderRepository.save(createdProductOrder);
    }
    
    Transaction createdTransaction = Transaction.builder()
      .productOrders(productOrders)
      .transactionType(transactionTypeRepository.findOneById(createTransactionRequest.getTransactionTypeId()))
      .user(getLoggedUserDetails())
      .value(transactionValue)
      .build();

    
    transactionsRepository.save(createdTransaction);

    return CreateTransactionResponse.builder()
        .id(createdTransaction.getId())
        .message("Pedido processado com sucesso")
        .build();
  }

  public CreateTransactionResponse findTransaction(UUID id) {
    Transaction createdTransaction = transactionsRepository.findById(id).orElseThrow(() -> new RuntimeException());
    return CreateTransactionResponse.builder()
        .id(createdTransaction.getId())
        .build();
  }

  public void deleteTransaction(UUID id) {
    transactionsRepository.deleteById(id);
  }

  public CreateTransactionResponse updateTransaction(UUID id, CreateTransactionRequest createTransactionRequest) {
     Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
    User userDetails =  (User) authentication.getPrincipal();
    Transaction createdTransaction = this.transactionsRepository
        .save(transactionMapper.toTransactionEntity(createTransactionRequest, userDetails));
    return CreateTransactionResponse.builder()
        .id(createdTransaction.getId())
        .message("Transação atualizada com sucesso")
        .build();
  }

  public List<Transaction> listTransactions() {
    return transactionsRepository.findAllByUserId(getLoggedUserDetails().getId());
  }
  
  public List<TransactionType> listAllTransactionTypes() {
    return transactionTypeRepository.findAll();
  }
  


}
