package com.questa.blogapi;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.questa.blogapi.model.Role;
import com.questa.blogapi.model.User;
import com.questa.blogapi.repository.UserRepository;

@Component
public class DataLoader implements ApplicationRunner {

	
	private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

	@Autowired
    private UserRepository userRepository;
    
    @Autowired
	private BCryptPasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
	private String adminEmail;

    @Value("${spring.mail.password}")
	private String adminPswd;

    public void run(ApplicationArguments args) {
    	userRepository.findByEmail(adminEmail).ifPresentOrElse(user -> log.info("Admin["+adminEmail+"] user present in Database::"+ user.toString()), () ->{
    		log.info("Admin["+adminEmail+"] user not exist in Database, Creating Admin user...");
    		User admin = new User();
    		admin.setFirstName("Admin");
    		admin.setLastName("Questa");
    		admin.setNickName("QuestAdmin");
    		admin.setEmail(adminEmail);
    		admin.setPassword(passwordEncoder.encode(adminPswd));
    		admin.setRole(Role.ADMIN);
    		admin.setBirthdate(new Date(1));
    		admin.setGrade(1);
    		admin.setCountry("India");
    		admin.setSchool("Questa");
    		userRepository.save(admin);
    	});
    }
}
