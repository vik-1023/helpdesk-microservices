package com.helpdesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.helpdesk.dto.UserRequestDTO;
import com.helpdesk.dto.UserResponseDTO;
import com.helpdesk.entity.User;
import com.helpdesk.exception.UserNotFoundException;
import com.helpdesk.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {

		this.userRepository = userRepository;
	}

	@Override
	public UserResponseDTO register(UserRequestDTO create) {
		Optional<User> existingUser = userRepository.findByEmail(create.getEmail());
		if (existingUser.isPresent()) {
			throw new UserNotFoundException("Email Already Exists");
		}

		User user = new User();

		user.setName(create.getName());
		user.setEmail(create.getEmail());
		user.setPassword(create.getPassword());
		user.setRole(create.getRole());

		User saveUser = userRepository.save(user);

		return new UserResponseDTO(saveUser.getId(), saveUser.getName(), saveUser.getEmail(), saveUser.getRole());
	}

	@Override
	public List<UserResponseDTO> getAllUsers() {
		List<User> userList = userRepository.findAll();
		return userList.stream()
				.map(user -> new UserResponseDTO(user.getId(), user.getEmail(), user.getName(), user.getRole()))
				.collect(Collectors.toList());
	}

	@Override
	public UserResponseDTO getUserById(Long id) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException ("user not found with :" + id));

		return new UserResponseDTO(existingUser.getId(), existingUser.getName(), existingUser.getEmail(),
				existingUser.getRole());
	}

	@Override
	public UserResponseDTO updateUser(Long id, UserRequestDTO user) {

	    User users = userRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    users.setName(user.getName());
	    users.setEmail(user.getEmail());

	    // update password only if provided
	    if (user.getPassword() != null) {
	        users.setPassword(user.getPassword());
	    }

	    users.setRole(user.getRole());

	    User updatedUser = userRepository.save(users);

	    return new UserResponseDTO(
	            updatedUser.getId(),
	            updatedUser.getName(),
	            updatedUser.getEmail(),
	            updatedUser.getRole()
	    );
	}

	@Override
	public void deleteUser(Long id) {

		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

		userRepository.delete(user);

	}

}
