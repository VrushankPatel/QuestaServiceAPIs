package com.questa.blogapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.Follower;

public interface FollowerRepository extends CrudRepository<Follower, Integer>{

	Iterable<Follower> findByQuestionId(Integer questionId);
	Iterable<Follower> findByUserId(Integer userId);
	Optional<Follower> findByQuestionIdAndUserId(Integer questionId, Integer userId);
	Integer countByQuestionId(Integer questionId);
}
