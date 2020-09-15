package com.questa.blogapi.model;

import java.io.Serializable;
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
@Table(name = "QUESTIONS")

public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_seq")
	@SequenceGenerator(name="questions_seq", sequenceName = "questions_seq")
	@Column(name = "QUESTION_ID", updatable = false)
	private Integer questionId;

	@Column(name = "USER_ID", nullable = false)
	private Integer userId;
	
	@Column(name = "SUBJECT", nullable = false)
	private String subject;

	@Column(name = "TOPIC", nullable = false)
	private String topic;

	@Column(name = "QUESTION_DESC", nullable = false, length = 1000)
	private String questionDesc;

	@Column(name = "CREATE_DATE", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date createDate;
	
	@JsonInclude()
	@Transient
	private List<Answer> answerList;
	
	@JsonInclude()
	@Transient
	private List<Follower> followerList;

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<Answer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<Answer> answerList) {
		this.answerList = answerList;
	}

	public List<Follower> getFollowerList() {
		return followerList;
	}

	public void setFollowerList(List<Follower> followerList) {
		this.followerList = followerList;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", userId=" + userId + ", subject=" + subject + ", topic=" + topic
				+ ", questionDesc=" + questionDesc + ", createDate=" + createDate + "]";
	}
	
	
}
