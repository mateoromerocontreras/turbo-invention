package com.tailorcvpro.backend.user.dto;

import com.tailorcvpro.backend.user.model.User;

import java.time.LocalDateTime;

public record UserResponseDto (
        Long id,
        String email,
        String firstName,
        String lastName,
        Boolean isActive,
        LocalDateTime createdAt,
        String baseResume
    ) {
        public static UserResponseDto fromEntity(User user) {
            return new UserResponseDto(
                    user.getId(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getIsActive(),
                    user.getCreatedAt(),
                    user.getBaseResume()
            );
        }
}
