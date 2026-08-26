package com.tailorcvpro.backend.tailoring.dto;

import com.tailorcvpro.backend.tailoring.model.TailoringSession;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response containing tailoring session details and AI generated output")
public record TailoringSessionResponseDto(
        @Schema(description = "Unique ID of the tailoring session", example = "101")
        Long id,

        @Schema(description = "ID of the owner user", example = "1")
        Long userId,

        @Schema(description = "Type of session", example = "RESUME")
        String sessionType,

        @Schema(description = "Job description submitted for tailoring", example = "Senior Software Engineer with Java...")
        String jobDescription,

        @Schema(description = "Original resume content", example = "Experienced backend engineer...")
        String originalResume,

        @Schema(description = "AI-generated tailored output content", example = "Tailored Resume Content...")
        String result,

        @Schema(description = "Total tokens consumed by LLM generation", example = "450")
        Integer tokensUsed,

        @Schema(description = "Timestamp when session was created", example = "2026-08-25T10:00:00")
        LocalDateTime createdAt
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