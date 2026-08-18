package com.tailorcvpro.backend.tailoring.dto;

public record TailoringSessionRequestDto(
        Long userId,
        String sessionType,
        String jobDescription,
        String originalResume
) {
}
