package com.ufba.stock_control.dtos.transactions;

import java.util.Date;

public record CreateTransactionItemResponse(String name, Integer quantity, Double value, Date boughtDate){}