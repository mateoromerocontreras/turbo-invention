package com.tailorcvpro.backend.tailoring.repository;

import com.tailorcvpro.backend.tailoring.model.TailoringSession;
import com.tailorcvpro.backend.user.model.User;
import com.tailorcvpro.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class TailoringSessionRepositoryTest {

    @Autowired
    private TailoringSessionRepository tailoringSessionRepository;

    @Autowired
    private UserRepository userRepository;

    private User createAndSaveUser() {
        User user = new User();
        user.setEmail("sub_test@example.com");
        user.setPassword("password123");
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);
        user.setIsStaff(false);
        user.setIsSuperuser(false);
        return userRepository.save(user);
    }

    @Test
    @DisplayName("Should find tailoring session by user id when session exists")
    void shouldFindByUserIdWhenSessionExists() {
        // GIVEN: save a user first, then create & save a Tailoring Session linked to that user
        User savedUser = createAndSaveUser();

        TailoringSession tailoringSession = new TailoringSession();
        tailoringSession.setUser(savedUser);
        tailoringSession.setCreatedAt(LocalDateTime.now());
        tailoringSessionRepository.save(tailoringSession);

        // WHEN: Call tailoringSessionRepository.findByUserId(savedUser.getId())
        List<TailoringSession> result = tailoringSessionRepository.findByUserId(savedUser.getId());

        // THEN: Assert session is present
        assertThat(result).isNotEmpty();
        assertThat(result.getFirst().getUser().getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    @DisplayName("Should find tailoring session by user id when session exists, ordered by creation")
    void shouldFindByUserIdWhenSessionExistsAndOrderByCreation() {
        // GIVEN: save a user first, then create & save a Tailoring Session linked to that user
        User savedUser = createAndSaveUser();

        // Older session (created 2 hours ago)
        TailoringSession olderSession = new TailoringSession();
        olderSession.setUser(savedUser);
        olderSession.setCreatedAt(LocalDateTime.now().minusHours(2));
        tailoringSessionRepository.save(olderSession);

        // Newer session (created now)
        TailoringSession newerSession = new TailoringSession();
        newerSession.setUser(savedUser);
        newerSession.setCreatedAt(LocalDateTime.now());
        tailoringSessionRepository.save(newerSession);

        // WHEN: Call findByUserIdOrderByCreatedAtDesc
        List<TailoringSession> result = tailoringSessionRepository.findByUserIdOrderByCreatedAtDesc(savedUser.getId());

        // THEN: Verify the list has size 2 and index 0 is newer than index 1
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCreatedAt()).isAfter(result.get(1).getCreatedAt());
    }


}
