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
    public TailoringSessionResponseDto createTailoringSession(TailoringSessionRequestDto sessionRequestDto) {
        User user = userRepository.findById(sessionRequestDto.userId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found for userId: "
                        + sessionRequestDto.userId()));
        String result = mockLlmPromptServiceImpl.generateTailoredContent(
                sessionRequestDto.sessionType(),
                sessionRequestDto.jobDescription(),
                sessionRequestDto.originalResume()
        );
        TailoringSession tailoringSession = new TailoringSession();
        tailoringSession.setUser(user);
        tailoringSession.setResult(result);
        tailoringSession.setTokensUsed(5);
        tailoringSession.setJobDescription(sessionRequestDto.jobDescription());
        tailoringSession.setCreatedAt(LocalDateTime.now());

        TailoringSession savedSession = tailoringSessionRepository.save(tailoringSession);

        return TailoringSessionResponseDto.fromEntity(savedSession);
    }

    @Override
    public List<TailoringSessionResponseDto> getTailoringSessionsByUserId(Long userId) {
        return tailoringSessionRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(TailoringSessionResponseDto::fromEntity)
                .toList();
    }

    @Override
    public TailoringSessionResponseDto getTailoringSessionById(Long id) {
        TailoringSession session = tailoringSessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tailoring session not found with id: " + id));
        return TailoringSessionResponseDto.fromEntity(session);
    }
}
