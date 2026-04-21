package com.helpdesk.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.helpdesk.auth.entity.AuthUser;
import com.helpdesk.auth.exception.ResourceNotFoundException;
import com.helpdesk.auth.repository.AuthUserRepository;
import com.helpdesk.auth.security.JwtUtil;

@Service
public class AuthService {
	@Autowired
	private AuthUserRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtil jwtUtil;

	public String signup(AuthUser user) {
		
		

		user.setPassword(encoder.encode(user.getPassword()));

		repository.save(user);

		return "User Registered Successfully";
	}

	public String login(AuthUser user) {

	    AuthUser dbUser = repository
	            .findByUsername(user.getUsername())
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "User not found"
	                    )
	            );

	    if (encoder.matches(
	            user.getPassword(),
	            dbUser.getPassword())) {
	    	
	    	
	    	

	        return jwtUtil.generateToken(
	                dbUser.getUsername(),
	                dbUser.getRole()
	        );
	    }

	    throw new ResourceNotFoundException(
	            "Invalid Password"
	    );
	}

}
