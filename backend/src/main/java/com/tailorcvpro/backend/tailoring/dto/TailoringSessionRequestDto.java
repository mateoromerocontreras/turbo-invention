package com.tailorcvpro.backend.tailoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request payload for creating a new tailoring session")
public record TailoringSessionRequestDto(
        @Schema(description = "ID of the user initiating the session", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "userId is required")
        Long userId,

        @Schema(description = "Type of tailoring session (e.g. RESUME, COVER_LETTER)", example = "RESUME", requiredMode = Schema.RequiredMode.
                REQUIRED)
        @NotBlank(message = "sessionType is required")
        String sessionType,

        @Schema(description = "Target job description text", example = "Senior Software Engineer with Java...", requiredMode = Schema.
                RequiredMode.REQUIRED)
        @NotBlank(message = "jobDescription is required")
        String jobDescription,

        @Schema(description = "Original resume/CV text content to be tailored", example = "Experienced backend engineer...", requiredMode =
                Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "originalResume is required")
        String originalResume
) {
}