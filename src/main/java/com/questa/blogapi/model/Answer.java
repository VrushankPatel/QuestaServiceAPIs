package com.questa.blogapi.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "ANSWERS")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answers_seq")
	@SequenceGenerator(name="answers_seq", sequenceName = "answers_seq")
	@Column(name = "ANSWER_ID", updatable = false)
	private Integer answerId;
	
	@Column(name = "QUESTION_ID", nullable = false)
	private Integer questionId;

	@Column(name = "USER_ID", nullable = false)
	private Integer userId;
	
	@Column(name = "ANSWER_DESC", nullable = false, length = 1000)
	private String answerDesc;

	@Column(name = "CREATE_DATE", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date createDate;

	@JsonInclude()
	@Transient
	private List<UserFeedback> userFeedbackList;

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAnswerDesc() {
		return answerDesc;
	}

	public void setAnswerDesc(String answerDesc) {
		this.answerDesc = answerDesc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<UserFeedback> getUserFeedbackList() {
		return userFeedbackList;
	}

	public void setUserFeedbackList(List<UserFeedback> userFeedbackList) {
		this.userFeedbackList = userFeedbackList;
	}

	@Override
	public String toString() {
		return "Answer [answerId=" + answerId + ", questionId=" + questionId + ", userId=" + userId + ", answerDesc="
				+ answerDesc + ", createDate=" + createDate + "]";
	}
	
	
}
