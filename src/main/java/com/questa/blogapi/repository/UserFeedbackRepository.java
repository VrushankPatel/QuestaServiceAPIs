package com.questa.blogapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.UserFeedback;

public interface UserFeedbackRepository extends CrudRepository<UserFeedback, Integer>{

	Optional<UserFeedback> findByAnswerId(Integer answerId);
	Optional<UserFeedback> findByAnswerIdAndUserId(Integer answerId, Integer userId);
	Optional<UserFeedback> findByQuestionIdAndAnswerIdAndUserId(Integer questionId, Integer answerId, Integer userId);
	Integer countByAnswerIdAndLiked(Integer answerId, Boolean liked);
	Integer countByAnswerIdAndUnliked(Integer answerId, Boolean unliked);
}
