package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.config.JwtTokenUtil;
import com.model.JwtRequest;
import com.model.JwtResponse;
import com.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
		}
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		if(userDetails != null) {
			String token = jwtTokenUtil.generateToken(userDetails);
			ResponseEntity<?> res = ResponseEntity.ok(new JwtResponse(token)); 
			return res;			
		} 
		return null;
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		} catch (Exception e) {
			throw new Exception("Other Exception");
		}
	}
	
}
