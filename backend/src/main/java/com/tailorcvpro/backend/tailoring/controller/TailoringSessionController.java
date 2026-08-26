package com.tailorcvpro.backend.tailoring.controller;

import com.tailorcvpro.backend.shared.exception.ErrorResponseDto;
import com.tailorcvpro.backend.tailoring.dto.TailoringSessionRequestDto;
import com.tailorcvpro.backend.tailoring.dto.TailoringSessionResponseDto;
import com.tailorcvpro.backend.tailoring.service.TailoringSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tailoring-sessions")
@RequiredArgsConstructor
@Tag(name = "Tailoring Sessions", description = "Endpoints for managing AI-powered resume and cover letter tailoring sessions")
public class TailoringSessionController {

    private final TailoringSessionService tailoringSessionService;

    @PostMapping
    @Operation(
            summary = "Create and run a tailoring session",
            description = "Generates customized resume or cover letter content based on the provided job description and original resume using LLM processing."
            )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tailoring session created and processed successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TailoringSessionResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload or missing required fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found for the supplied userId",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<TailoringSessionResponseDto> createTailoringSession(
            @Valid @RequestBody TailoringSessionRequestDto sessionRequestDto) {
        TailoringSessionResponseDto createdSession = tailoringSessionService.createTailoringSession(sessionRequestDto);
        return ResponseEntity.ok(createdSession);
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Retrieve tailoring sessions by user ID",
            description = "Fetches a list of all tailoring sessions created by the specified user, ordered by creation date descending."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of tailoring sessions retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TailoringSessionResponseDto.class))
                    )
            )
    })
    public ResponseEntity<List<TailoringSessionResponseDto>> getTailoringSessionByUserId(
            @Parameter(description = "ID of the user whose sessions are being requested", example = "1", required = true)
            @PathVariable Long userId) {
        List<TailoringSessionResponseDto> sessions = tailoringSessionService.getTailoringSessionsByUserId(userId);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a tailoring session by ID",
            description = "Fetches the full details and generated results of a specific tailoring session."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tailoring session retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TailoringSessionResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tailoring session not found for the specified ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<TailoringSessionResponseDto> getTailoringSessionById(
            @Parameter(description = "Unique ID of the tailoring session", example = "101", required = true)
            @PathVariable Long id) {
        TailoringSessionResponseDto session = tailoringSessionService.getTailoringSessionById(id);
        return ResponseEntity.ok(session);
    }
}