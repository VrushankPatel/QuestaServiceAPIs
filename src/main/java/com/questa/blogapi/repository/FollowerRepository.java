package com.questa.blogapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.Follower;

public interface FollowerRepository extends CrudRepository<Follower, Integer>{

	Iterable<Follower> findByQuestionId(Integer questionId);
	Iterable<Follower> findByUserId(Integer userId);

}
