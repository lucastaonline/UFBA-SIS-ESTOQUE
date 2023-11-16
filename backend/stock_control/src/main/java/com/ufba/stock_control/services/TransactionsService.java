package com.ufba.stock_control.services;

import com.ufba.stock_control.dtos.transactions.CreateTransactionItemRequest;
import com.ufba.stock_control.dtos.transactions.CreateTransactionRequest;
import com.ufba.stock_control.dtos.transactions.CreateTransactionResponse;
import com.ufba.stock_control.dtos.transactions.CreatedTransactionResponse;
import com.ufba.stock_control.entities.Product;
import com.ufba.stock_control.entities.ProductOrder;
import com.ufba.stock_control.entities.Transaction;
import com.ufba.stock_control.entities.TransactionDirection;
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
import java.util.Date;

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
    User userDetails = (User) authentication.getPrincipal();
    return userDetails;
  }


  public CreateTransactionResponse createTransaction(CreateTransactionRequest createTransactionRequest) {
    Double transactionValue = 0.0;
    List<ProductOrder> productOrders = new ArrayList<>();
    TransactionType foundTransactionType = this.transactionTypeRepository.findOneById(createTransactionRequest.getTransactionTypeId());
    

    Transaction createdTransaction = Transaction.builder()
      .transactionType(foundTransactionType)
      .user(getLoggedUserDetails())
      .value(transactionValue)
      .createdAt(new Date())
      .updateAt(new Date())
      .build();

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

      if (foundTransactionType.getDirection() == TransactionDirection.INLET) {
        foundProduct.setStock(foundProduct.getStock() + item.quantity());
      } else {
        foundProduct.setStock(foundProduct.getStock() - item.quantity());
      }

      ProductOrder createdProductOrder = ProductOrder.builder()
        .product(foundProduct)
        .value(unitaryPrice)
        .quantity(item.quantity())
        .transaction(createdTransaction)
        .build();
      
      transactionValue += unitaryPrice;
      
      productOrders.add(createdProductOrder);
      this.productsRepository.save(foundProduct);
    }
    createdTransaction.setValue(transactionValue);
    transactionsRepository.save(createdTransaction);
    productsOrderRepository.saveAll(productOrders);

    return CreateTransactionResponse.builder()
        .id(createdTransaction.getId())
        .message("Pedido processado com sucesso")
        .build();
  }

  public Transaction findTransaction(UUID id) {
    Transaction createdTransaction = transactionsRepository.findById(id).orElseThrow(() -> new RuntimeException());
    return createdTransaction;
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

  public List<CreatedTransactionResponse> listTransactions() {
    List<Transaction> transactions =  transactionsRepository.findAllWithProductOrdersByUserId(getLoggedUserDetails().getId());
    return transactions.stream()
      .map(transactionMapper::toTransactionTransacionResponse)
      .toList();
  }
  
  public List<TransactionType> listAllTransactionTypes() {
    return transactionTypeRepository.findAll();
  }
  


}
