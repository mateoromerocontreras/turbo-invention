package com.tailorcvpro.backend.tailoring.service;

import com.tailorcvpro.backend.tailoring.dto.TailoringSessionRequestDto;
import com.tailorcvpro.backend.tailoring.dto.TailoringSessionResponseDto;
import com.tailorcvpro.backend.tailoring.model.TailoringSession;
import com.tailorcvpro.backend.tailoring.repository.TailoringSessionRepository;
import com.tailorcvpro.backend.user.model.User;
import com.tailorcvpro.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TailoringSessionServiceImpl implements TailoringSessionService{
    private final TailoringSessionRepository tailoringSessionRepository;
    private final UserRepository userRepository;
    private final MockLlmPromptServiceImpl mockLlmPromptServiceImpl;

    @Override
    @Transactional
    public TailoringSessionResponseDto createTailoringSession(TailoringSessionRequestDto sessionRequest) {
        User user = userRepository.findById(sessionRequest.userId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found for userId: "
                        + sessionRequest.userId()));
        String result = mockLlmPromptServiceImpl.generateTailoredContent(
                "string",
                "string",
                "string");
        TailoringSession tailoringSession = new TailoringSession();
        tailoringSession.setUser(user);
        tailoringSession.setResult(result);
        tailoringSession.setTokensUsed(5);
        tailoringSession.setJobDescription(sessionRequest.jobDescription());
        tailoringSession.setCreatedAt(LocalDateTime.now());

        TailoringSession savedSession = tailoringSessionRepository.save(tailoringSession);

        return TailoringSessionResponseDto.fromEntity(savedSession);
    }

    @Override
    public List<TailoringSessionResponseDto> getTailoringSessionsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public TailoringSessionResponseDto getTailoringSessionById(Long id) {
        return null;
    }
}
