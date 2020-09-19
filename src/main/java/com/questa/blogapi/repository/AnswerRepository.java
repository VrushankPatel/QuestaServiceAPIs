package com.questa.blogapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.Answer;

public interface AnswerRepository  extends CrudRepository<Answer, Integer>{

	Iterable<Answer> findByQuestionId(Integer questionId);
	Iterable<Answer> findByUserId(Integer userId);
	Integer countByQuestionId(Integer questionId);
}
