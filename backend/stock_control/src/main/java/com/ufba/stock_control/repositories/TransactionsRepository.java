package com.ufba.stock_control.repositories;

import com.ufba.stock_control.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface TransactionsRepository extends JpaRepository<Transaction, UUID> {
}
