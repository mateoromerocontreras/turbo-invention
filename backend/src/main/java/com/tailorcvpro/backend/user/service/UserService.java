package com.tailorcvpro.backend.user.service;

import com.tailorcvpro.backend.user.dto.UserCreateDto;
import com.tailorcvpro.backend.user.dto.UserResponseDto;
import com.tailorcvpro.backend.user.dto.UserUpdateDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserCreateDto user);
    UserResponseDto getUserById(Long id);
    UserResponseDto getUserByEmail(String email);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(Long id, UserUpdateDto updateUser);
    void deleteUser(Long id);
}
