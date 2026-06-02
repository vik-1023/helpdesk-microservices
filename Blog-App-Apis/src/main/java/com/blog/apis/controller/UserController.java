package com.blog.apis.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.apis.payloads.UserRequestDto;
import com.blog.apis.payloads.UserResponseDto;
import com.blog.apis.services.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserServiceImpl userServiceImpl;

	public UserController(UserServiceImpl userServiceImpl) {
		super();
		this.userServiceImpl = userServiceImpl;

	}

	@PostMapping
	public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto userRequestDto) {
		UserResponseDto response = userServiceImpl.createUser(userRequestDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@GetMapping
	public ResponseEntity<List<UserResponseDto>> getAllUser() {
		List<UserResponseDto> respoDto = userServiceImpl.getAllUsers();
		return ResponseEntity.ok(respoDto);
	}

}
