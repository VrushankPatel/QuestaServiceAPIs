package com.questa.blogapi.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class QuestaException extends RuntimeException {

	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private final QuestaExceptionResponse exceptionResponse;
	
	public QuestaException(String errorMessage, Integer ErrorCode) {
		exceptionResponse = new QuestaExceptionResponse(errorMessage, ErrorCode); 
	}

	public QuestaExceptionResponse getExceptionResponse() {
		return exceptionResponse;
	}
	
}

