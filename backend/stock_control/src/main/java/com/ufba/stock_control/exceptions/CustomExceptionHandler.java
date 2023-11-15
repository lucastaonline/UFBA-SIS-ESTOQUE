package com.ufba.stock_control.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ufba.stock_control.dtos.errors.ErrorResponse;
import com.ufba.stock_control.dtos.errors.FieldErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler {
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse>  handleValidationErrors(MethodArgumentNotValidException exception) {
    List<FieldErrorResponse> response = new ArrayList<>();
        exception.getBindingResult()
            .getAllErrors()
            .forEach(error -> {
                response.add(FieldErrorResponse.builder()
                    .field(((FieldError) error).getField())
                    .message(error.getDefaultMessage()).build());
        });

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.builder()
                .timeStamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("FieldException")
                .errors(response)
                .build());
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleInvalidCredential(UnauthorizedException ex) {

      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body(ErrorResponse.builder()
              .timeStamp(System.currentTimeMillis())
              .status(HttpStatus.UNAUTHORIZED.value()).message("UnauthorizedException")
              .errors(Collections.singletonList(FieldErrorResponse.builder()
                  .message(ex.getMessage())
                  .build())).build());
  }

}
