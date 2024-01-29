package com.ruchi.exceptions;

public class CommentNotFound extends RuntimeException {
	private String message;
	
	public CommentNotFound(String message) {
		super(message);
		this.message = message;
	}
}
