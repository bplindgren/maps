package com.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.User;
import com.model.ClientUserDTO;
import com.model.UserDTO;
import com.repository.UserRepository;
import com.service.UserService;
import com.utils.HeaderUtil;

@RestController
@CrossOrigin
public class UserController {
	
	private final UserService userService;
	private final UserRepository userRepository;
	
	public UserController(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/users/{username}")
	public ResponseEntity<UserDTO> getUser(@PathVariable String username) throws Exception {
		Optional<User> user = userService.findByUsername(username);
		if(user.isPresent()) {
			UserDTO userDTO = new UserDTO(user.get());
			
			URI location = new URI("/users/" + userDTO.getUsername());
			HttpHeaders responseHeaders = HeaderUtil.createAlert("User found with username: " + userDTO.getUsername(), userDTO.getUsername());
			return ResponseEntity.created(location).headers(responseHeaders).body(userDTO);			
			
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
	public ResponseEntity<User> createUser(@RequestBody ClientUserDTO userDTO) throws Exception {	
		if(userRepository.findOneByUsernameIgnoreCase(userDTO.getUsername()).isPresent()) {
			throw new Exception("Username already in use");
		} else if(userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
			throw new Exception("Email already in use");
		} else {
			User newUser = userService.createUser(userDTO);
			
			URI location = new URI("/users/" + newUser.getUsername());
			HttpHeaders responseHeaders = HeaderUtil.createAlert("User created with username: " + newUser.getUsername(), newUser.getUsername());
			return ResponseEntity.created(location).headers(responseHeaders).body(newUser);
		}
	}	

}
