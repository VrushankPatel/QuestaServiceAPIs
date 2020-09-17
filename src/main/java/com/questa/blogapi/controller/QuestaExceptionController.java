package com.questa.blogapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.questa.blogapi.exception.QuestaException;

@ControllerAdvice
public class QuestaExceptionController {

	
	private static final Logger log = LoggerFactory.getLogger(QuestaExceptionController.class);

	@ExceptionHandler(value = QuestaException.class)
	public ResponseEntity<Object> emailExisEexception(QuestaException exception) {
		log.info("QuestaExceptionController:: handle QuestaException["+exception.getMessage()+"]");
		return new ResponseEntity<>(exception.getExceptionResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> exception(Exception exception) {
		log.info("QuestaExceptionController:: handle Exception["+exception.getMessage()+"]");
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
