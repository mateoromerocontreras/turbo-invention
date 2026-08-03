package com.tailorcvpro.backend.user.service;

import com.tailorcvpro.backend.user.dto.UserCreateDto;
import com.tailorcvpro.backend.user.dto.UserResponseDto;
import com.tailorcvpro.backend.user.model.User;
import com.tailorcvpro.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Should create user successfully when email is unique")
    void shouldCreateUserSuccessfully() {
        // GIVEN
        UserCreateDto dto = new UserCreateDto("new@example.com",
                "pass123", "John", "Doe");
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(dto.email());
        savedUser.setFirstName(dto.firstName());
        savedUser.setLastName(dto.lastName());
        savedUser.setCreatedAt(LocalDateTime.now());

        // Stub mock behavior: email does not exist yet, and save returns savedUser
        when(userRepository.existsByEmail(dto.email())).
                thenReturn(false);
        when(userRepository.save(any(User.class))).
                thenReturn(savedUser);

        // WHEN
        UserResponseDto result = userService.createUser(dto);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo("new@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when creating user with duplicate email")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // GIVEN
        UserCreateDto dto = new UserCreateDto("existing@example.com",
                "pass123", "John", "Doe");

        // TODO: Stub userRepository.existsByEmail to return true!

        // WHEN & THEN
        // TODO: Use assertThatThrownBy(() -> userService.createUser(dto))
        //       and verify it throws IllegalArgumentException with message "User already exists"
    }

    @Test
    @DisplayName("Should return user when valid ID is given")
    void shouldGetUserByIdSuccessfully() {
        // TODO: Implement getUserById success scenario
    }

    @Test
    @DisplayName("Should throw exception when getUserById is called with nonexistent ID")
    void shouldThrowExceptionWhenUserNotFoundById() {
        // TODO: Implement getUserById not found scenario
    }
}
