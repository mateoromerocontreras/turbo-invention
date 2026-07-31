package com.tailorcvpro.backend.user.dto;

public record UserUpdateDto(
        String firstName,
        String lastName,
        String baseResume
) {
}
