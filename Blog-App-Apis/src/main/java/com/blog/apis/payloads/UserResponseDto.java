package com.blog.apis.payloads;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
	  private Long id;

	    private String name;

	    private String email;

	    private String about;

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;
}
