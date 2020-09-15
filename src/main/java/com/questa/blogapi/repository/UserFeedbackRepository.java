package com.questa.blogapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.UserFeedback;

public interface UserFeedbackRepository extends CrudRepository<UserFeedback, Integer>{

	Iterable<UserFeedback> findByAnswerId(Integer answerId);

}
