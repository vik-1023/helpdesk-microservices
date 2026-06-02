package com.blog.apis.services;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.blog.apis.entity.User;
import com.blog.apis.payloads.UserRequestDto;
import com.blog.apis.payloads.UserResponseDto;
import com.blog.apis.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepo userRepo;
	private final ModelMapper modelMapper;

	public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper) {
		super();
		this.userRepo = userRepo;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		// RequestDto -> Entity
		User user = modelMapper.map(userRequestDto, User.class);
		User savedUser = userRepo.save(user);
		return modelMapper.map(savedUser, UserResponseDto.class);
	}

	@Override
	public UserResponseDto updateUser(UserRequestDto userRequestDto, Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
		user.setName(userRequestDto.getName());
		user.setAbout(userRequestDto.getAbout());
		user.setEmail(userRequestDto.getEmail());
		user.setPassword(userRequestDto.getPassword());
		User updatedUser = userRepo.save(user);
		return modelMapper.map(updatedUser, UserResponseDto.class);
	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		List<User> allUsers = userRepo.findAll();

		List<UserResponseDto> alluser = allUsers.stream().map(usrs -> modelMapper.map(usrs, UserResponseDto.class))
				.toList();

		return alluser;
	}

	@Override
	public UserResponseDto getUserById(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
		userRepo.delete(user);
	}

}
