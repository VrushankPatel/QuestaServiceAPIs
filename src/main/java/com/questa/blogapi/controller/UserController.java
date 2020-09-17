package com.questa.blogapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.questa.blogapi.exception.QuestaException;
import com.questa.blogapi.model.AuthenticationRequest;
import com.questa.blogapi.model.AuthenticationResponse;
import com.questa.blogapi.model.User;
import com.questa.blogapi.service.UserService;
import com.questa.blogapi.util.ConstantUtil;

@RestController
public class UserController {
	@Autowired
	private UserService userDetailsService;

	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = ConstantUtil.LOGIN_ENDPOINT , method = RequestMethod.POST)
	private ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws QuestaException {
		log.info("Calling "+ ConstantUtil.LOGIN_ENDPOINT +" endpoint");
		log.info("Creating auth token for "+authenticationRequest.toString());
		AuthenticationResponse authenticationResponse = userDetailsService.createAuthenticationToken(authenticationRequest);
		log.info("Created auth token for "+authenticationResponse.toString());
		return ResponseEntity.ok(authenticationResponse);
	}

	@RequestMapping(value = ConstantUtil.SIGNUP_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public ResponseEntity<Object> createUser(@RequestBody User user) throws QuestaException {
		log.info("Calling "+ ConstantUtil.SIGNUP_ENDPOINT +" endpoint");
		return userDetailsService.createUser(user);

	}
	
	@RequestMapping(value = ConstantUtil.GETUSER_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public ResponseEntity<Object> getUserdetails(@PathVariable Integer userId) throws QuestaException {
		log.info("Calling "+ ConstantUtil.GETUSER_ENDPOINT +" endpoint");
		return userDetailsService.getUserdetails(userId);

	}

	@RequestMapping(value = ConstantUtil.GETFULLUSER_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public ResponseEntity<Object> getFullUserdetails(@PathVariable Integer userId) throws QuestaException {
		log.info("Calling "+ ConstantUtil.GETFULLUSER_ENDPOINT +" endpoint");
		return userDetailsService.getFullUserdetails(userId);

	}
}
