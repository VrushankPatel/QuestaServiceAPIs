package com.questa.blogapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.QuestionFeedback;

public interface QuestionFeedbackRepository extends CrudRepository<QuestionFeedback, Integer>{

	Optional<QuestionFeedback> findByQuestionIdAndUserId(Integer questionId, Integer userId);
	Integer countByQuestionIdAndLiked(Integer questionId, Boolean liked);
	Integer countByQuestionIdAndUnliked(Integer questionId, Boolean unliked);
}
