package com.questa.blogapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer>{
	List<Question> findByUserIdOrderByCreateDateDesc(Integer userId);
	List<Question> findBySubjectAndTopicIgnoreCaseContainingOrderByCreateDateDesc(String subject, String topic);
	List<Question> findByOrderByCreateDateDesc();
	List<Question> findDistinctByQuestionId(List<Integer> questionId);
	Optional<Question> findByQuestionId(Integer questionId);
}
