package com.questa.blogapi.repository;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.User; 

public interface UserRepository extends CrudRepository<User, Integer>{
	Optional<User> findByEmail(String email);
}
