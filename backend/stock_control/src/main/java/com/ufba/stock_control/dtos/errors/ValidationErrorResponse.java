package com.ufba.stock_control.dtos.errors;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationErrorResponse {
  private int status;
	private String message;
	private long timeStamp;
	private List<FieldErrorResponse> errors;
}
