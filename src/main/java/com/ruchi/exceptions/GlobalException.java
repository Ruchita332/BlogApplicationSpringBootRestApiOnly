package com.ruchi.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {

			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);

		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<ApiResponse> invalidImageFileException(ResourceNotFoundException exception) {
		ApiResponse response = ApiResponse.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND)
				.timestamp(new Date()).build();

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {

		// Check if the exception is related to a unique constraint violation
		if (ex.getCause() instanceof ConstraintViolationException) {
			ConstraintViolationException constraintEx = (ConstraintViolationException) ex.getCause();
			String errorMessage = constraintEx.getMessage(); // Error message with details
			// Handle the unique constraint violation error here
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
		}
		// Handle other types of exceptions if needed
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("An error occurred");
	}
	
	@ExceptionHandler(PasswordMisMatchException.class)
	protected ResponseEntity<ApiResponse> passwordMisMatch(PasswordMisMatchException exception) {
		ApiResponse response = ApiResponse.builder().message(exception.getMessage()).status(HttpStatus.NOT_ACCEPTABLE)
				.timestamp(new Date()).build();

		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	}
	
	
	
	
	
	
	
	@ExceptionHandler (DuplicateItemException.class)
	protected ResponseEntity<ApiResponse> invalidEmailException (InvalidEmailException exception){
	ApiResponse response = ApiResponse.builder().message(exception.getMessage()).status(HttpStatus.CONFLICT)
							.timestamp(new Date()).build();
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
//	@ExceptionHandler(InvalidPasswordException.class)
//	protected ResponseEntity<ApiResponse> invalidPasswordException(InvalidPasswordException exception) {
//
//		ApiResponse response = ApiResponse.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND)
//				.timestamp(new Date()).build();
//
//		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//
//	}
//
//	@ExceptionHandler(UserNotFoundException.class)
//	protected ResponseEntity<ApiResponse> userNotFound(UserNotFoundException exception) {
//
//		ApiResponse response = ApiResponse.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND)
//				.timestamp(new Date()).build();
//
//		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//
//	}
//

//
//	
//
//	@ExceptionHandler(ImageNotFoundException.class)
//	protected ResponseEntity<ApiResponse> profilePictureNotFound(ImageNotFoundException exception) {
//		ApiResponse response = ApiResponse.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND)
//				.timestamp(new Date()).build();
//
//		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//
//	}


}
