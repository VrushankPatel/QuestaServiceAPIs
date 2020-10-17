package com.questa.blogapi.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.questa.blogapi.model.QuestaResponse;

public class QuestaException extends RuntimeException {

	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private final QuestaResponse exceptionResponse;
	
	public QuestaException(String errorMessage, Integer ErrorCode) {
		exceptionResponse = new QuestaResponse(errorMessage, ErrorCode,false,null); 
	}

	public QuestaResponse getExceptionResponse() {
		return exceptionResponse;
	}
	
}

