package com.questa.blogapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer>{
	List<Question> findByUserIdOrderByCreateDateDesc(Integer userId);
	List<Question> findBySubjectIgnoreCaseContainingAndTopicIgnoreCaseContainingAndQuestionDescIgnoreCaseContainingOrderByCreateDateDesc(String subject, String topic, String questionDesc);
	List<Question> findByOrderByCreateDateDesc();
	List<Question> findDistinctByQuestionIdInOrderByCreateDateDesc(List<Integer> questionId);
	Optional<Question> findByQuestionId(Integer questionId);
}
