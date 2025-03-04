package com.questa.blogapi.model;

public class QuestaResponse {
	private final String message;
	private final Integer code;
	private final Boolean status;
	private final UserProgressLevel userProgressLevel;
	
	public QuestaResponse(String message, Integer code, Boolean status, UserProgressLevel userProgressLevel) {
		super();
		this.message = message;
		this.code = code;
		this.status = status;
		this.userProgressLevel = userProgressLevel;
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

	public UserProgressLevel getUserProgressLevel() {
		return userProgressLevel;
	}
	
}
