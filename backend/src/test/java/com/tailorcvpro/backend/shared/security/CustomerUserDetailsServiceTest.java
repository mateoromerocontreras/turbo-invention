package com.tailorcvpro.backend.shared.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.tailorcvpro.backend.user.model.User;
import com.tailorcvpro.backend.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@ExtendWith(MockitoExtension.class)
public class CustomerUserDetailsServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Test
	@DisplayName("Should return UserDetails when valid email is provided")
	public void shouldReturnUserDetailsSuccessfully() {
		// GIVEN
		User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("123456");
        user.setIsActive(true);
        // STUB
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        // WHEN
        UserDetails result = customerUserDetailsService.loadUserByUsername("test@example.com");
        // THEN 
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(user.getEmail());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
        verify(userRepository, times(1)).findByEmail("test@example.com");
	}

    @Test
    @DisplayName("Should throw a UsernameNotFoundException when invalid email")
    public void shouldThrowUsernameNotFoundExceptionWhenInvalidEmail() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerUserDetailsService.loadUserByUsername("test@example.com"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found with email: " + "test@example.com");
    }
}
