package com.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.Authority;
import com.entity.User;
import com.repository.AuthorityRepository;
import com.repository.UserRepository;
import com.security.AuthorityConstants;

import service.dto.UserDTO;

@Service
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	private final AuthorityRepository authorityRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Optional<User> findByUsername(String username) {
		return userRepository.findOneByUsernameIgnoreCase(username);
	}
	
	public <Optional>User createUser(UserDTO userDTO) {
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		
		if (userDTO.getEmail() != null) {
			user.setEmail(userDTO.getEmail().toLowerCase());
		}
		String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
		user.setPassword(encryptedPassword);
		//Authority information
		Set<Authority> authorities = new HashSet<Authority>();
		authorityRepository.findById(AuthorityConstants.USER).ifPresent(authorities::add);
		user.setAuthorities(authorities);
		
		//timestamp information
		user.setCreatedBy(userDTO.getUsername());
		user.setLastModifiedBy(userDTO.getUsername());
		userRepository.save(user);
		return user;
	}
	
}
