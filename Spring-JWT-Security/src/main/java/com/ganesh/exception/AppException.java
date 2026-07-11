package com.ganesh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppException {
	
	
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> handalException(Exception e) {
		
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.UNAUTHORIZED);
	}

}
