package com.ufba.stock_control.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ufba.stock_control.entities.TransactionType;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, UUID> {
  TransactionType findOneById(UUID id);
}
