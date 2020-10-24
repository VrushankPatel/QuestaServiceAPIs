package com.questa.blogapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.AnswerFeedback;

public interface AnswerFeedbackRepository extends CrudRepository<AnswerFeedback, Integer>{

	List<AnswerFeedback> findByAnswerIdAndUserId(Integer answerId, Integer userId);
	List<AnswerFeedback> findByReportDescNotNull();
	List<AnswerFeedback> findByAnswerIdAndReportDescNotNull(Integer answerId);
	//Integer countByAnswerIdAndReportDescNotNull(Integer answerId);
	Integer countByAnswerIdAndLiked(Integer answerId, Boolean liked);
	Integer countByAnswerIdAndUnliked(Integer answerId, Boolean unliked);
}
