package com.questa.blogapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.questa.blogapi.exception.QuestaException;
import com.questa.blogapi.model.AuthenticationRequest;
import com.questa.blogapi.model.AuthenticationResponse;
import com.questa.blogapi.model.User;
import com.questa.blogapi.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userDetailsService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	private ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws QuestaException {
		AuthenticationResponse authenticationResponse = userDetailsService.createAuthenticationToken(authenticationRequest);
		return ResponseEntity.ok(authenticationResponse);
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> createUser(@RequestBody User user) throws QuestaException {
		return userDetailsService.createUser(user);

	}

}
