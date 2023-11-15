package com.ufba.stock_control.repositories;

import com.ufba.stock_control.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<Transaction, UUID> {
}
