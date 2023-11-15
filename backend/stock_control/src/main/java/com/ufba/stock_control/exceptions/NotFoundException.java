package com.ufba.stock_control.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;

@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException {
  public NotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
  
}
