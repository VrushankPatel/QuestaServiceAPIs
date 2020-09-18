package com.questa.blogapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer>{
	Iterable<Question> findByUserId(Integer userId);
	List<Question> findBySubjectIgnoreCaseContainingAndTopicIgnoreCaseContaining(String subject, String topic);
}
