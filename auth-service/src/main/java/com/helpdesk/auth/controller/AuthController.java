package com.helpdesk.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpdesk.auth.entity.AuthUser;
import com.helpdesk.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService service;

	@PostMapping("/signup")
	public String signup(@RequestBody AuthUser user) {

		return service.signup(user);
	}

	@PostMapping("/login")
	public String login(@RequestBody AuthUser user) {

		return service.login(user);
	}
}
