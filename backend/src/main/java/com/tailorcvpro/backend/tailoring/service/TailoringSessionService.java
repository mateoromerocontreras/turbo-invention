package com.tailorcvpro.backend.tailoring.service;

import com.tailorcvpro.backend.tailoring.dto.TailoringSessionRequestDto;
import com.tailorcvpro.backend.tailoring.dto.TailoringSessionResponseDto;

import java.util.List;

public interface TailoringSessionService {
    TailoringSessionResponseDto createTailoringSession(TailoringSessionRequestDto sessionRequest);

    List<TailoringSessionResponseDto> getTailoringSessionsByUserId(Long userId);

    TailoringSessionResponseDto getTailoringSessionById(Long id);
}
