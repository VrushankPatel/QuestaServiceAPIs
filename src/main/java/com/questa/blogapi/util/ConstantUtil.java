package com.questa.blogapi.util;

import java.util.List;

public class ConstantUtil {
	
	//UserController end-points
	public static final String LOGIN_ENDPOINT = "/login";
	public static final String SIGNUP_ENDPOINT = "/signup";
	public static final String GETUSER_ENDPOINT = "/getuser/{userId}";
	public static final String GETFULLUSER_ENDPOINT = "/getfulluser/{userId}";
	
	//QuestionController end-points
	public static final String CREATE_QUESTION_ENDPOINT = "/createquestion";
	public static final String CREATE_ANSWER_ENDPOINT = "/createanswer";
	public static final String CREATE_FOLLWER_ENDPOINT = "/createfollower";
	public static final String CREATE_FEEDBACK_ENDPOINT = "/createfeedback";
	public static final String FIND_ALL_QUESTIONS_ENDPOINT = "/findallquestions/{userId}";
	public static final String FIND_ALL_BY_FOLLOWER_ENDPOINT = "/findallbyfollower/{userId}";
	public static final String FIND_ALL_BY_ANSWER_ENDPOINT = "/findallbyanswer/{userId}";
	public static final String FIND_ALL_BY_SUBJECT_TOPIC_ENDPOINT = "/findallbysubjecttopic";
	
	public static final List<String> AUTH_IGNORE_ENDPOINT = List.of(LOGIN_ENDPOINT,SIGNUP_ENDPOINT);

	public static final String PRODUCE_APP_JSON = "application/json";
	
	
	public static final Integer SUCCESS_CODE = 200;
	public static final Integer EMAIL_ERROR_CODE = 531;
	public static final String EMAIL_ERROR_MESSAGE = "Email already exist in Database";
	
	public static final Integer INCORRECT_LOGIN_ERROR_CODE = 532;
	public static final String INCORRECT_LOGIN_ERROR_MESSAGE = "Incorrect username or password";
	
	public static final Integer AUTH_FAILED_ERROR_CODE = 555;
	public static final String AUTH_FAILED_ERROR_MESSAGE = "Authentication failed";
	
	
	public static final Integer USER_NOFOUNT_ERROR_CODE = 533;
	public static final String USER_NOFOUNT_ERROR_MESSAGE = "Username not found";
	
	public static final String USER_CREATED_MESSAGE = "User successfully created...";
	public static final String QUESTION_CREATED_MESSAGE = "Question successfully created...";
	public static final String ANSWER_CREATED_MESSAGE = "Answer successfully created...";
	public static final String USER_FEEDBACK_CREATED_MESSAGE = "User feedback successfully created...";
	public static final String FOLLOWER_CREATED_MESSAGE = "Follower successfully created...";
	public static final String FOLLOWER_DELETED_MESSAGE = "Follower successfully deleted...";
	
}
