package com.sb.maps.controller;

import com.sb.maps.config.JwtTokenUtil;
import com.sb.maps.dto.SignUpDto;
import com.sb.maps.dto.UserDto;
import com.sb.maps.entity.User;
import com.sb.maps.service.UserService;
import com.sb.maps.utils.HeaderUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@Controller
public class AuthController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) throws Exception {
        User existingUser = userService.findUserByEmail(signUpDto.getEmail());

        if (existingUser != null && existingUser.getUsername() != null) {
            throw new Exception("Username already in use");
        } else if (existingUser != null && existingUser.getEmail() != null) {
            throw new Exception("Email already in use");
        }

        User newUser = userService.saveUser(signUpDto);
        UserDto userDto = new UserDto();
        userDto.setFirstName(newUser.getFirstName());
        userDto.setLastName(newUser.getLastName());
        userDto.setUsername(newUser.getUsername());
        userDto.setEmail(newUser.getEmail());
        userDto.setToken(jwtTokenUtil.generateToken(newUser));
        URI location = new URI("/users/" + newUser.getUsername());

        HttpHeaders responseHeaders = HeaderUtil.createAlert("User created with username: " + newUser.getUsername(), newUser.getUsername());
        return ResponseEntity.created(location).headers(responseHeaders).body(userDto);
    }
}
