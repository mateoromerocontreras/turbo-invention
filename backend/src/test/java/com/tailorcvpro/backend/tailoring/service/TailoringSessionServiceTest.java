package com.tailorcvpro.backend.tailoring.service;

import com.tailorcvpro.backend.tailoring.dto.TailoringSessionRequestDto;
import com.tailorcvpro.backend.tailoring.dto.TailoringSessionResponseDto;
import com.tailorcvpro.backend.tailoring.model.TailoringSession;
import com.tailorcvpro.backend.tailoring.repository.TailoringSessionRepository;
import com.tailorcvpro.backend.user.model.User;
import com.tailorcvpro.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TailoringSessionServiceTest {

    @Mock
    private TailoringSessionRepository tailoringSessionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LlmPromptService llmPromptService;

    @InjectMocks
    private TailoringSessionServiceImpl tailoringSessionService;

    @Test
    @DisplayName("Should create tailoring session successfully when valid user id is provided")
    void shouldCreateTailoringSessionSuccessfully() {
        // GIVEN
        User user = new User();
        user.setId(1L);

        TailoringSessionRequestDto requestDto = new TailoringSessionRequestDto(
                1L, "RESUME", "Java Engineer JD", "Base Resume Content"
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(llmPromptService.generateTailoredContent("RESUME", "Java Engineer JD", "Base Resume Content"))
                .thenReturn("Tailored CV Output");
        when(tailoringSessionRepository.save(any(TailoringSession.class)))
                .thenAnswer(inv -> {
                    TailoringSession s = inv.getArgument(0);
                    s.setId(100L);
                    return s;
                });

        // WHEN
        TailoringSessionResponseDto result = tailoringSessionService.createTailoringSession(requestDto);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(100L);
        assertThat(result.result()).isEqualTo("Tailored CV Output");
        verify(tailoringSessionRepository, times(1)).save(any(TailoringSession.class));
    }

    @Test
    @DisplayName("Should throw exception when creating session for non-existent user")
    void shouldThrowExceptionWhenUserNotFoundOnCreate() {
        TailoringSessionRequestDto requestDto = new TailoringSessionRequestDto(
                99L, "RESUME", "Java Engineer JD", "Base Resume Content"
        );

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tailoringSessionService.createTailoringSession(requestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User not found for userId: 99");
    }

    @Test
    @DisplayName("Should return session when valid ID is given")
    void shouldGetSessionByIdSuccessfully() {
        // GIVEN
        User savedUser = new User();
        savedUser.setId(1L);

        TailoringSession savedSession = new TailoringSession();
        savedSession.setId(100L);
        savedSession.setUser(savedUser);
        savedSession.setCreatedAt(LocalDateTime.now());

        when(tailoringSessionRepository.findById(100L)).thenReturn(Optional.of(savedSession));

        // WHEN
        TailoringSessionResponseDto result = tailoringSessionService.getTailoringSessionById(100L);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(100L);

        verify(tailoringSessionRepository, times(1)).findById(100L);
    }

    @Test
    @DisplayName("Should throw exception when getTailoringSessionById is called with nonexistent ID")
    void shouldThrowExceptionWhenGetTailoringSessionByIdIsCalledWithNonexistentId() {
        when(tailoringSessionRepository.findById(100L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> tailoringSessionService
                .getTailoringSessionById(100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Tailoring session not found with id: " + 100L);
    }

    // Add any additional tests for getTailoringSessionsByUserId
    @Test
    @DisplayName("Should return list of sessions when valid user ID is given")
    void shouldGetAllSessionsSuccessfully() {
        // GIVEN
        User savedUser = new User();
        savedUser.setId(1L);

        TailoringSession firstSession = new TailoringSession();
        firstSession.setId(100L);
        firstSession.setUser(savedUser);
        firstSession.setCreatedAt(LocalDateTime.now());

        TailoringSession secondSession = new TailoringSession();
        secondSession.setId(101L);
        secondSession.setUser(savedUser);
        secondSession.setCreatedAt(LocalDateTime.now());

        when(tailoringSessionRepository.findByUserIdOrderByCreatedAtDesc(1L))
                .thenReturn(List.of(firstSession, secondSession));

        // WHEN
        List<TailoringSessionResponseDto> results = tailoringSessionService.getTailoringSessionsByUserId(1L);

       // THEN
       assertThat(results).hasSize(2);
       assertThat(results.getFirst().id()).isEqualTo(100L);
        verify(tailoringSessionRepository, times(1)).findByUserIdOrderByCreatedAtDesc(1L);
    }

    @Test
    @DisplayName("Should return empty list when user has no sessions")
    void shouldReturnEmptyListWhenUserHasNoSessions() {
        when(tailoringSessionRepository.findByUserIdOrderByCreatedAtDesc(1L))
                .thenReturn(List.of());

        List<TailoringSessionResponseDto> results = tailoringSessionService.getTailoringSessionsByUserId(1L);

        assertThat(results).isEmpty();
    }
}
