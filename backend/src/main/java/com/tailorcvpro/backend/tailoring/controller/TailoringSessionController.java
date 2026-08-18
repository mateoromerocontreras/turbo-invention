package com.tailorcvpro.backend.tailoring.controller;

import com.tailorcvpro.backend.tailoring.dto.TailoringSessionRequestDto;
import com.tailorcvpro.backend.tailoring.dto.TailoringSessionResponseDto;
import com.tailorcvpro.backend.tailoring.service.TailoringSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tailoring-sessions")
@RequiredArgsConstructor
public class TailoringSessionController {

    private final TailoringSessionService tailoringSessionService;

    @PostMapping
    public ResponseEntity<TailoringSessionResponseDto> createTailoringSession(
            @RequestBody TailoringSessionRequestDto sessionRequestDto) {
        TailoringSessionResponseDto createdSession = tailoringSessionService.createTailoringSession(sessionRequestDto);
        return ResponseEntity.ok(createdSession);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TailoringSessionResponseDto>> getTailoringSessionByUserId(@PathVariable Long userId) {
        List<TailoringSessionResponseDto>  sessions = tailoringSessionService.getTailoringSessionsByUserId(userId);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("{id}")
    public ResponseEntity<TailoringSessionResponseDto> getTailoringSessionById(@PathVariable Long id) {
        TailoringSessionResponseDto session = tailoringSessionService.getTailoringSessionById(id);
        return ResponseEntity.ok(session);
    }
}
