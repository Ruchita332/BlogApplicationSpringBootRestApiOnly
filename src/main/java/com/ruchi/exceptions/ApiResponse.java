package com.ruchi.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
	private String message;
	private HttpStatus status;
	private Date timestamp;
}
