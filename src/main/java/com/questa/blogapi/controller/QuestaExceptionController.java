package com.questa.blogapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.questa.blogapi.exception.QuestaException;

@ControllerAdvice
public class QuestaExceptionController {

	@ExceptionHandler(value = QuestaException.class)
	public ResponseEntity<Object> emailExisEexception(QuestaException exception) {
		return new ResponseEntity<>(exception.getExceptionResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> exception(Exception exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
