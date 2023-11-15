package com.ufba.stock_control.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {
  
  private final String message;
  private final HttpStatus statusCode;

  public BaseException(String message, HttpStatus statusCode) {
    super(message);
    this.message = message;
    this.statusCode = statusCode;
  }
}
