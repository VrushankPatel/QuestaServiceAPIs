package com.questa.blogapi.model;

public class QuestionSearch {

	private Integer userId;
	private String subject;
	private String topic;
	private String questionDesc;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	@Override
	public String toString() {
		return "QuestionSearch [userId=" + userId + ", subject=" + subject + ", topic=" + topic + ", questionDesc="
				+ questionDesc + "]";
	}

}
