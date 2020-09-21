package com.questa.blogapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.Follower;

public interface FollowerRepository extends CrudRepository<Follower, Integer>{

	List<Follower> findByQuestionId(Integer questionId);
	List<Follower> findByUserId(Integer userId);
	Optional<Follower> findByQuestionIdAndUserId(Integer questionId, Integer userId);
	Integer countByQuestionId(Integer questionId);
}
