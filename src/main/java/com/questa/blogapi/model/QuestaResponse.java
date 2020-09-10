package com.questa.blogapi.model;

public class QuestaResponse {
	private final String message;
	private final Integer code;
	private final Boolean status;
	
	public QuestaResponse(String message, Integer code, Boolean status) {
		super();
		this.message = message;
		this.code = code;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}
	
	public Integer getCode() {
		return code;
	}

	public Boolean getStatus() {
		return status;
	}

}
