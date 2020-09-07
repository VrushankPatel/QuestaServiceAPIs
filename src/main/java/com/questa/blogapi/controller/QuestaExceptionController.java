package com.questa.blogapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.questa.blogapi.exception.EmailAlreadyExistException;

@ControllerAdvice
public class QuestaExceptionController {

	@ExceptionHandler(value = EmailAlreadyExistException.class)
	public ResponseEntity<Object> emailExisEexception(EmailAlreadyExistException exception) {
		return new ResponseEntity<>("Email already exist in Database", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = UsernameNotFoundException.class)
	public ResponseEntity<Object> usernameNotExistException(UsernameNotFoundException exception) {
		return new ResponseEntity<>("Username doesn't exist, please retry", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> exception(Exception exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
}
