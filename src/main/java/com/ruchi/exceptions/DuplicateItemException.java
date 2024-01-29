package com.ruchi.exceptions;

public class DuplicateItemException extends RuntimeException {
	private String message;
	
	public DuplicateItemException(String message) {
		super(message);
		this.message = message;
	}
}
