package com.questa.blogapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer>{
	List<Question> findByUserIdOrderByCreateDateDesc(Integer userId);
	List<Question> findBySubjectIgnoreCaseContainingAndTopicIgnoreCaseContainingOrderByCreateDateDesc(String subject, String topic);
	List<Question> findByOrderByCreateDateDesc();
}
