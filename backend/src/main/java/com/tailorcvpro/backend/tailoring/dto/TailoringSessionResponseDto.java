package com.tailorcvpro.backend.tailoring.dto;

import com.tailorcvpro.backend.tailoring.model.TailoringSession;

import java.time.LocalDateTime;

public record TailoringSessionResponseDto(
        Long id,
        Long userId,
        String sessionType,
        String jobDescription,
        String originalResume,
        String result,
        Integer tokensUsed,
        LocalDateTime createdAr
) {
    public static TailoringSessionResponseDto fromEntity(TailoringSession session) {
        return new TailoringSessionResponseDto(
                session.getId(),
                session.getUser().getId(),
                session.getSessionType(),
                session.getJobDescription(),
                session.getOriginalResume(),
                session.getResult(),
                session.getTokensUsed(),
                session.getCreatedAt()
        );
    }
}
