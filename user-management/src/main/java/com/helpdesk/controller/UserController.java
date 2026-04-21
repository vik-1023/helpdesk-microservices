package com.helpdesk.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpdesk.dto.UserRequestDTO;
import com.helpdesk.dto.UserResponseDTO;
import com.helpdesk.service.UserService;



@RequestMapping("/api/users")
@RestController
public class UserController {
	
	

	private final UserService userService;

	public UserController(UserService userService) {

		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO requestDTO) {
		UserResponseDTO user = userService.register(requestDTO);
		return ResponseEntity.ok(user);

	}

	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	 @GetMapping("/{id}")
	    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {

	        return ResponseEntity.ok(userService.getUserById(id));
	    }
	 
	 
	    @PutMapping("/{id}")
	    public ResponseEntity<UserResponseDTO> updateUser(
	            @PathVariable Long id,
	            @RequestBody UserRequestDTO dto) {

	        return ResponseEntity.ok(userService.updateUser(id, dto));
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

	        userService.deleteUser(id);

	        return ResponseEntity.ok("User deleted successfully");
	    }

}
