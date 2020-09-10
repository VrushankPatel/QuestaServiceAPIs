package com.questa.blogapi.model;

public class AuthenticationResponse {

	private final String token;
	private final Integer code;
	private final Boolean status;
	
	public AuthenticationResponse(String token, Integer code,Boolean status) {
		this.token = token;
		this.code = code;
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public Integer getCode() {
		return code;
	}

	public Boolean getStatus() {
		return status;
	}

	
	
}
