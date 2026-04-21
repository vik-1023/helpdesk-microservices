package com.helpdesk.service;

import java.util.List;

import com.helpdesk.dto.UserRequestDTO;
import com.helpdesk.dto.UserResponseDTO;

public interface UserService {
	
UserResponseDTO register(UserRequestDTO create);

List<UserResponseDTO> getAllUsers();

UserResponseDTO getUserById(Long id);

UserResponseDTO updateUser(Long id ,UserRequestDTO user);

void deleteUser(Long id);


}
