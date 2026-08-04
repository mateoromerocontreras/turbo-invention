package com.tailorcvpro.backend.user.service;

import com.tailorcvpro.backend.user.dto.UserCreateDto;
import com.tailorcvpro.backend.user.dto.UserResponseDto;
import com.tailorcvpro.backend.user.dto.UserUpdateDto;
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

        when(userRepository.existsByEmail(dto.email())).
                thenReturn(true);

        // WHEN & THEN
        // TODO: Use assertThatThrownBy(() -> userService.createUser(dto))
        //       and verify it throws IllegalArgumentException with message "User already exists"
        assertThatThrownBy(() -> userService.createUser(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User already exists");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should return user when valid ID is given")
    void shouldGetUserByIdSuccessfully() {
        // TODO: Implement getUserById success scenario
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
        when(userRepository.findById(1L)).thenReturn(Optional.of(savedUser));


        // WHEN
        UserResponseDto result = userService.getUserById(1L);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.email()).isEqualTo(dto.email());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when getUserById is called with nonexistent ID")
    void shouldThrowExceptionWhenUserNotFoundById() {
        // TODO: Implement getUserById not found scenario

        when(userRepository.findById(1L)).
                thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User not found with id: " + 1L);

    }

    @Test
    @DisplayName("Should return user when valid email is given")
    void shouldGetUserByEmailSuccessfully() {
        // GIVEN: create a dummy User entity
        User user = new User();
        user.setId(1L);
        user.setEmail("john@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        // TODO: Stub userRepository.findByEmail("john@example.com")
        //  to return Optional.of(user)
        when(userRepository.findByEmail("john@example.com"))
                .thenReturn(Optional.of(user));

        // WHEN: Call userService.getUserByEmail("john@example.com")
        UserResponseDto result = userService.getUserByEmail("john@example.com");

        // THEN: Assert result isNotNull, email is "john@example.
        // com", and verify findByEmail was called
        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo("john@example.com");

        verify(userRepository, times(1)).findByEmail("john@example.com");

    }

    @Test
    @DisplayName("Should throw exception when getUserByEmail is called with non existant email")
    void shouldThrowExceptionWhenUserNotFoundByEmail() {
        // TODO: Stub userRepository.findByEmail("missing@example.com") to return Optional.empty()
        when(userRepository.findByEmail("missing@example.com"))
                .thenReturn(Optional.empty());

        // TODO: Use assertThatThrownBy(() -> userService.getUserByEmail("missing@example.com"))
        //       and verify it throws IllegalArgumentException with
        //       message "User not found with email: missing@example.com"
        assertThatThrownBy(() -> userService.getUserByEmail("missing@example.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User not found with email: missing@example.com");
    }

    @Test
    @DisplayName("Should return list of all users")
    void shouldGetAllUsersSuccessfully() {
        User u1 = new User(); u1.setEmail("u1@example.com");
        User u2 = new User(); u2.setEmail("u2@example.com");

        when(userRepository.findAll()).thenReturn(List.of(u1, u2));

        List<UserResponseDto> results = userService.getAllUsers();

        assertThat(results).hasSize(2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update user fields successfully")
    void shouldUpdateUserSuccessfully() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFirstName("OldName");

        UserUpdateDto updateDto = new UserUpdateDto("NewName",
                "NewLastName", "New Resume");

        when(userRepository.findById(1L)).thenReturn(Optional.
                of(existingUser));
        when(userRepository.save(any(User.class))).
                thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDto result = userService.updateUser(1L,
                updateDto);

        assertThat(result.firstName()).isEqualTo("NewName");
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    @DisplayName("Should delete user successfully when ID exists")
    void shouldDeleteUserSuccessfully() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent user")
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        UserUpdateDto updateDto = new UserUpdateDto("NewName",
                "NewLastName", "New Resume");
        when(userRepository.findById(99L)).thenReturn(Optional.
                empty());

        assertThatThrownBy(() -> userService.updateUser(99L,
                updateDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User with id 99 does not exist");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent user")
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteUser(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User with id 99 does not exist");

        verify(userRepository, never()).deleteById(anyLong());
    }


}
