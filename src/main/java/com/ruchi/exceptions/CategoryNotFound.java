package com.ruchi.exceptions;

public class CategoryNotFound extends RuntimeException {
	private String message;
	
	public CategoryNotFound(String message) {
		super(message);
		this.message = message;
	}
}
