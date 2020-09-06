package com.questa.blogapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.questa.blogapi.repository.UserRepository;

@SpringBootApplication
public class QuestaServiceApIsApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestaServiceApIsApplication.class, args);
	}

}
