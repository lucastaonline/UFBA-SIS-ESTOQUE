package com.ufba.stock_control.controllers;

import com.ufba.stock_control.dtos.transactions.CreateTransactionRequest;
import com.ufba.stock_control.dtos.transactions.CreateTransactionResponse;
import com.ufba.stock_control.entities.Transaction;
import com.ufba.stock_control.services.TransactionsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Tag(name =  "Transactions",  description = "Transaction related routes")
@RestController
@RequestMapping(
  path = "/transactions",
  consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE
)
@CrossOrigin(origins = "*")
public class TransactionsController {
  @Autowired
  private TransactionsService transactionsService;

  @PostMapping
  public ResponseEntity<CreateTransactionResponse> addTransaction(@RequestBody CreateTransactionRequest transactionDTO) {
    return ResponseEntity.status(HttpStatus.OK).body(transactionsService.createTransaction(transactionDTO));
  }
  @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<CreateTransactionResponse> findTransaction(@PathVariable("id") UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(transactionsService.findTransaction(id));
  }
  @GetMapping(consumes = MediaType.ALL_VALUE)
  public ResponseEntity<List<Transaction>> findAllTransactions() {
    return ResponseEntity.status(HttpStatus.OK).body(transactionsService.listTransactions());
  }
  @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<Void> deleteTransaction(@PathVariable("id") UUID id) {
    transactionsService.deleteTransaction(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/{id}")
  public CreateTransactionResponse updateTransaction(@PathVariable("id") UUID id, @RequestBody CreateTransactionRequest transactionDTO) {
    return transactionsService.updateTransaction(id, transactionDTO);
  }
  
}