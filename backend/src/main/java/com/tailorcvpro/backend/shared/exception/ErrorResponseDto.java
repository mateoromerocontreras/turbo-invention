package com.tailorcvpro.backend.shared.exception;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        LocalDateTime timeStamp,
        int status,
        String error,
        String message
) {}
