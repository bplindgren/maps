package com.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.User;
import com.repository.UserRepository;
import com.service.UserService;

import service.dto.UserDTO;

@RestController
public class UserController {
	
	private final UserService userService;
	private final UserRepository userRepository;
	
	public UserController(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/users/{username}")
	public ResponseEntity<UserDTO> getUser(@PathVariable String username) throws Exception{
		Optional<User> user = userService.findByUsername(username);
		if(user.isPresent()) {
			UserDTO userDTO = new UserDTO(user.get());
			ResponseEntity<UserDTO> response = new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
			return response;
		} else {
			throw new Exception("User was not found");
		}
	}
	
	/**
	 * {POST /users} - Creates a new user
	 * 
	 * @param userDTO the user to create
	 * @return the ResponseEntity with status "201" (Created) and with the new User object
	 * @throws Exception if login or email are already in use
	 */
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) throws Exception {	
		if(userRepository.findOneByUsernameIgnoreCase(userDTO.getUsername()).isPresent()) {
			throw new Exception("Username already in use");
		} else if(userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
			throw new Exception("Email already in use");
		} else {
			User newUser = userService.createUser(userDTO);
			return ResponseEntity.created(new URI("/users" +  newUser.getUsername()))
					.body(newUser);
		}
	}	

}
