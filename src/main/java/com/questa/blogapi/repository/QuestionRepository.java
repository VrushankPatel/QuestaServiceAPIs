package com.questa.blogapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer>{
	Iterable<Question> findByUserId(Integer userId);
}
