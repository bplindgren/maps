package com.web.rest;

import org.springframework.web.bind.annotation.RestController;

import com.repository.UserRepository;
import com.service.UserService;

@RestController
public class UserResource {
	
	private UserService userService;
	private UserRepository userRepository;
	
	public UserResource(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}
	
//	@PostMapping("/users"

	
	

}
