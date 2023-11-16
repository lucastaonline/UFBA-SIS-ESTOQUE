package com.ufba.stock_control.controllers;

import com.ufba.stock_control.entities.TransactionType;
import com.ufba.stock_control.services.TransactionsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name =  "Transactions Type",  description = "Transaction Aux routes")
@RestController
@RequestMapping(
  path = "/transactionTypes",
  consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE
)
public class TransactionTypeController {

  @Autowired
  private TransactionsService transactionsService;
  @GetMapping(consumes = MediaType.ALL_VALUE)
  public ResponseEntity<List<TransactionType>> findAllTransactionTypes() {
    return ResponseEntity.status(HttpStatus.OK).body(transactionsService.listAllTransactionTypes());
  }
}
