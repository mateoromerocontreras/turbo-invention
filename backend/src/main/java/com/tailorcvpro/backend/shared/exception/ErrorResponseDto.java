package com.tailorcvpro.backend.shared.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Standard error response model")
public record ErrorResponseDto(
        @Schema(description = "Timestamp of the error", example = "2026-08-25T10:30:00")
        LocalDateTime timeStamp,

        @Schema(description = "HTTP status code", example = "404")
        int status,

        @Schema(description = "Error title / reason phrase", example = "Not found")
        String error,

        @Schema(description = "Detailed error message", example = "User not found for userId: 1")
        String message
) {}