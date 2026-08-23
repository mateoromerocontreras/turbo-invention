package com.tailorcvpro.backend.shared.security;

import static org.assertj.core.api.Assertions.assertThat;
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



@ExtendWith(MockitoExtension.class)
public class CustomerUserDetailsServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Test
	@DisplayName("Should return UserDetails when valid email is provided")
	public void shouldReturnUserDetailsSuccessfully() {
		// TODO 1. GIVEN: Create a dummy User.java entity with email, password, and active status.
		// TODO 2. STUB: Tell Mockito: when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(dummyUser)).
		// TODO 3. WHEN: Call userDetailsService.loadUserByUsername("test@example.com").
		// TODO 4. THEN:
		// TODO     • Assert the returned CustomUserDetails.java is not null.
		// TODO     • Assert result.getUsername() equals "test@example.com".
		// TODO     • Assert result.getPassword() equals the user's password.
		// TODO     • Verify userRepository.findByEmail(...) was called exactly once.
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
	}
}
