package com.ufba.stock_control.controllers;

import com.ufba.stock_control.entities.Transaction;
import com.ufba.stock_control.services.TransactionsService;

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
public class TransactionsController {
  @Autowired
  private TransactionsService transactionsService;

  @PostMapping
  public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
    return ResponseEntity.status(HttpStatus.OK).body(transactionsService.createTransaction(transaction));
  }
  @GetMapping("/{id}")
  public ResponseEntity<Transaction> findTransaction(@PathVariable("id") UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(transactionsService.findTransaction(id));
  }
  @GetMapping
  public ResponseEntity<List<Transaction>> findAllTransactions() {
    return ResponseEntity.status(HttpStatus.OK).body(transactionsService.listTransactions());
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTransaction(@PathVariable("id") UUID id) {
    transactionsService.deleteTransaction(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
  

}