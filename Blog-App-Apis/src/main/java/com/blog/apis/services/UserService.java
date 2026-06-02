package com.blog.apis.services;

import java.util.List;

import com.blog.apis.payloads.UserRequestDto;
import com.blog.apis.payloads.UserResponseDto;

public interface UserService {
	
	UserResponseDto createUser(UserRequestDto userRequestDto);
	UserResponseDto updateUser(UserRequestDto userRequestDto,Long id);
	List<UserResponseDto>getAllUsers();
	UserResponseDto getUserById(Long id);
	void deleteUser(Long id);

}
