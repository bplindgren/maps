package com.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.User;
import com.repository.UserRepository;

import service.dto.UserDTO;

@Service
@Transactional
public class UserService {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository) {//, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
//		this.passwordEncoder = passwordEncoder;
	}
	
	public User createUser(UserDTO userDTO) {
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		if (userDTO.getEmail() != null) {
			user.setEmail(userDTO.getEmail().toLowerCase());
		}
//		String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
		
		user.setPassword(userDTO.getPassword());
		user.setCreatedBy(userDTO.getUsername());
		user.setLastModifiedBy(userDTO.getUsername());
		userRepository.save(user);
		return user;
	}
	
}
