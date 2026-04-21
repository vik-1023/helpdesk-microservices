package com.helpdesk.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.helpdesk.dto.UserResponseDTO;

@FeignClient(
	    name = "USER-MANAGEMENT",
	    fallbackFactory = UserClientFallbackFactory.class
	)
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserResponseDTO getUserById(@PathVariable Long id);
}
