package com.questa.blogapi.repository;
import org.springframework.data.repository.CrudRepository;

import com.questa.blogapi.model.User; 

public interface UserRepository extends CrudRepository<User, Integer>{

}
