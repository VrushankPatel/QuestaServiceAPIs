package com.questa.blogapi.model;

public class AuthenticationResponse {

	private final String token;
	private final Integer code;
	private final Boolean status;
	private final Integer userId;

	public AuthenticationResponse(String token, Integer code, Boolean status, Integer userId) {
		this.token = token;
		this.code = code;
		this.status = status;
		this.userId = userId;
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

	public Integer getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [token=" + token + ", code=" + code + ", status=" + status + ", userId=" + userId
				+ "]";
	}

}
