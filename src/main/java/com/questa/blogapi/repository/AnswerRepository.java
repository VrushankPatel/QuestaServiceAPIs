package com.questa.blogapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.Answer;

public interface AnswerRepository  extends CrudRepository<Answer, Integer>{

	Iterable<Answer> findByQuestionId(Integer questionId);
	Iterable<Answer> findByUserId(Integer userId);
	Optional<Answer> findByQuestionIdAndUserId(Integer questionId, Integer userId);
	Integer countByQuestionId(Integer questionId);
}
