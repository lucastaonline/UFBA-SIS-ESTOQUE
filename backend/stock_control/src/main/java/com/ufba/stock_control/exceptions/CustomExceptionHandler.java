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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.ufba.stock_control.dtos.errors.ValidationErrorResponse;
import com.ufba.stock_control.dtos.errors.ErrorResponse;
import com.ufba.stock_control.dtos.errors.FieldErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler {
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse>  handleValidationErrors(MethodArgumentNotValidException exception) {
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
            .body(ValidationErrorResponse.builder()
                .timeStamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("FieldException")
                .errors(response)
                .build());
  }


  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse>  handleValidationErrors(MethodArgumentTypeMismatchException ex) {
  

        return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(ErrorResponse.builder()
              .timeStamp(System.currentTimeMillis())
              .status(HttpStatus.BAD_REQUEST.value())
              .message(ex.getMessage()).build());
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleInvalidCredential(UnauthorizedException ex) {

      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body(ErrorResponse.builder()
              .timeStamp(System.currentTimeMillis())
              .status(HttpStatus.UNAUTHORIZED.value())
              .build());
  }
  
  
  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ErrorResponse> handleConflictError(ConflictException ex) {

      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(ErrorResponse.builder()
              .timeStamp(System.currentTimeMillis())
              .status(HttpStatus.CONFLICT.value())
              .message(ex.getMessage()).build());
  }
  
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundError(NotFoundException ex) {

      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(ErrorResponse.builder()
              .timeStamp(System.currentTimeMillis())
              .status(HttpStatus.NOT_FOUND.value()).message("NotFoundException")
              .message(ex.getMessage()).build());
  }

}
