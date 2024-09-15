package com.sb.maps.service;

import com.sb.maps.dto.LoginDto;
import com.sb.maps.dto.SignUpDto;
import com.sb.maps.dto.UserDto;
import com.sb.maps.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(SignUpDto signUpDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
