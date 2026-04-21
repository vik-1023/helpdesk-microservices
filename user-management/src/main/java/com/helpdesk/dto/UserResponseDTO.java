package com.helpdesk.dto;

import com.helpdesk.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {

	private Long id;
	private String name;
	private String email;
	private Role role;
	
}
