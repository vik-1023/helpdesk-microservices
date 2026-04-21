package com.helpdesk.client;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.helpdesk.dto.UserResponseDTO;
import feign.FeignException;

@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable cause) {

        return new UserClient() {

            @Override
            public UserResponseDTO getUserById(Long id) {

                if (cause instanceof FeignException feignException) {

                    if (feignException.status() == 404) {

                        throw new RuntimeException(
                                "User not found with ID: " + id
                        );
                    }
                }

                throw new RuntimeException(
                        "User service unavailable. Try later."
                );
            }
        };
    }
}