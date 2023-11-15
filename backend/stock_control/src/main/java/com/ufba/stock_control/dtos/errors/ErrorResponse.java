package com.ufba.stock_control.dtos.errors;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
  private int status;
	private String message;
	private long timeStamp;
}
