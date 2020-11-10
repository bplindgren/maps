package com.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.Authority;
import com.entity.User;
import com.model.ClientUserDTO;
import com.repository.AuthorityRepository;
import com.repository.UserRepository;
import com.security.AuthorityConstants;

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
		Optional<User> user = userRepository.findOneByUsernameIgnoreCase(username);
		return user;
	}
	
	@SuppressWarnings("hiding")
	public <Optional>User createUser(ClientUserDTO userDTO) {
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
		user.setLocation(userDTO.getLocation());
		userRepository.save(user);
		return user;
	}
	
	@SuppressWarnings("hiding")
	public User updateUser(ClientUserDTO userDTO) {
		User updatedUser = userRepository.findOneById(userDTO.getId());
		
		updatedUser.setFirstName(userDTO.getFirstName());
		updatedUser.setLastName(userDTO.getLastName());
		updatedUser.setUsername(userDTO.getUsername());
		updatedUser.setEmail(userDTO.getEmail());
		updatedUser.setLastModifiedBy(userDTO.getUsername());
		updatedUser.setLocation(userDTO.getLocation());
		
		return updatedUser;
	}
	
}
