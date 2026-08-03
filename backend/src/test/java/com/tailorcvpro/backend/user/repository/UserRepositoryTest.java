package com.tailorcvpro.backend.user.repository;

import com.tailorcvpro.backend.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should find user by email when user exists")
    void shouldFindByEmailWhenUserExists() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("hashedpassword123");
        user.setCreatedAt(LocalDateTime.now());
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setIsActive(true);
        user.setIsStaff(false);
        user.setIsSuperuser(false);
        userRepository.save(user);


        Optional<User> result = userRepository.findByEmail("test@example.com");


        assertThat(result.isPresent()).isTrue();
        assertThat(result)
                .isPresent()
                .hasValueSatisfying(foundUser ->
                        assertThat(foundUser.getEmail()).isEqualTo("test@example.com")
                );
    }

    @Test
    @DisplayName("Should return empty optional when email does not exist")
    void shouldReturnEmptyWhenEmailDoesNotExist() {
        Optional<User> result = userRepository.findByEmail("test_negative@example.com");
        assertThat(result.isPresent()).isFalse();
        assertThat(result).isEmpty();
    }
}
