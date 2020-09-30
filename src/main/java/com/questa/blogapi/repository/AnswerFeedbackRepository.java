package com.questa.blogapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.AnswerFeedback;

public interface AnswerFeedbackRepository extends CrudRepository<AnswerFeedback, Integer>{

	Optional<AnswerFeedback> findByQuestionIdAndAnswerIdAndUserId(Integer questionId, Integer answerId, Integer userId);
	Integer countByAnswerIdAndLiked(Integer answerId, Boolean liked);
	Integer countByAnswerIdAndUnliked(Integer answerId, Boolean unliked);
}
