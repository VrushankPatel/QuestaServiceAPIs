package com.questa.blogapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USERFEEDBACK")
public class UserFeedback {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_seq")
	@SequenceGenerator(name="feedback_seq", sequenceName = "feedback_seq")
	@Column(name = "FEEDBACK_ID", updatable = false)
	private Integer feedbackrId;
	
	@Column(name = "ANSWER_ID", nullable = false)
	private Integer answerId;
	
	@Column(name = "QUESTION_ID", nullable = false)
	private Integer questionId;

	@Column(name = "USER_ID", nullable = false)
	private Integer userId;
	
	@Column(name = "LIKED", nullable = false)
	private Boolean liked = false;
	
	@Column(name = "UNLIKED", nullable = false)
	private Boolean unliked = false;
	
	@Column(name = "REPORT_DESC", length = 1000)
	private String reportDesc;

	public Integer getFeedbackrId() {
		return feedbackrId;
	}

	public void setFeedbackrId(Integer feedbackrId) {
		this.feedbackrId = feedbackrId;
	}

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

	public Boolean getLiked() {
		return liked;
	}

	public void setLiked(Boolean liked) {
		this.liked = liked;
	}

	public Boolean getUnliked() {
		return unliked;
	}

	public void setUnliked(Boolean unliked) {
		this.unliked = unliked;
	}

	public String getReportDesc() {
		return reportDesc;
	}

	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}

	@Override
	public String toString() {
		return "UserFeedback [feedbackrId=" + feedbackrId + ", answerId=" + answerId + ", questionId=" + questionId
				+ ", userId=" + userId + ", liked=" + liked + ", unliked=" + unliked + ", reportDesc=" + reportDesc
				+ "]";
	}
	
}
