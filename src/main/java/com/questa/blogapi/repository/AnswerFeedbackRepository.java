package com.questa.blogapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.AnswerFeedback;

public interface AnswerFeedbackRepository extends CrudRepository<AnswerFeedback, Integer>{

	Optional<AnswerFeedback> findByAnswerIdAndUserId(Integer answerId, Integer userId);
	List<AnswerFeedback> findByReportDescNotNull();
	Integer countByAnswerIdAndLiked(Integer answerId, Boolean liked);
	Integer countByAnswerIdAndUnliked(Integer answerId, Boolean unliked);
}
