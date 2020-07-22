package com.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.entity.User;
import com.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional
    public Optional<User> findOneByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
