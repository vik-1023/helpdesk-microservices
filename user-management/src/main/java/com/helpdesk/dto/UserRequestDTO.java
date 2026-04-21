package com.helpdesk.dto;

import com.helpdesk.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequestDTO {
	
	private String name;
	private String email;
	private String password;
	private Role role;
	
	

}
