package com.ufba.stock_control.dtos.transactions;

import java.util.UUID;

public record CreateTransactionItemRequest(UUID productId, Integer quantity) {}
