package com.questa.blogapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.QuestionFeedback;

public interface QuestionFeedbackRepository extends CrudRepository<QuestionFeedback, Integer>{

	List<QuestionFeedback> findByQuestionIdAndUserId(Integer questionId, Integer userId);
	Integer countByQuestionIdAndLiked(Integer questionId, Boolean liked);
	Integer countByQuestionIdAndUnliked(Integer questionId, Boolean unliked);
}
