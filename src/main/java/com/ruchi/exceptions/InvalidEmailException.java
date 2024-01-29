package com.ruchi.exceptions;

public class InvalidEmailException extends RuntimeException {
	
	private String message;
	
	public InvalidEmailException (String message) {
		super(message);
		this.message = message;
	}
}
