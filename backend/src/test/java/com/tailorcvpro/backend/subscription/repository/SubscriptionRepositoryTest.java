package com.tailorcvpro.backend.subscription.repository;

import com.tailorcvpro.backend.subscription.model.Subscription;
import com.tailorcvpro.backend.user.model.User;
import com.tailorcvpro.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

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
    @DisplayName("Should find subscription by user id when subscriptions exists")
    void shouldFindByUserIdWhenSubscriptionsExists() {
        // GIVEN: save a user first, then create & save a Subscription linked to that user
        User savedUser = createAndSaveUser();

        Subscription subscription = new Subscription();
        subscription.setUser(savedUser);
        subscription.setPlan("PRO");
        subscription.setCreatedAt(LocalDateTime.now());
        subscriptionRepository.save(subscription);

        // WHEN: Call subscriptionRepository.findByUserId(savedUser.getId())
        Optional<Subscription> result = subscriptionRepository.findByUserId(savedUser.getId());

        // THEN: Assert present plan is "PRO"
        assertThat(result).isPresent();
        assertThat(result.get().getPlan()).isEqualTo("PRO");
    }

    @Test
    @DisplayName("Should find subscription by Lemonsqueezy subscription id when exists")
    void shouldFindByLemonSqueezySubscriptionIdWhenExists() {
        User savedUser = createAndSaveUser();

        Subscription subscription = new Subscription();
        subscription.setUser(savedUser);
        subscription.setPlan("PRO");
        subscription.setCreatedAt(LocalDateTime.now());
        subscription.setLemonSqueezySubscriptionId("sub_123456");
        subscriptionRepository.save(subscription);

        // WHEN: Call subscriptionRepository.findByLemonSqueezySubscriptionId(savedUser.getLemonsqueezyId())
        Optional<Subscription> result = subscriptionRepository.findByLemonSqueezySubscriptionId("sub_123456");

        // THEN: Assert present plan is "PRO"
        assertThat(result).isPresent();
        assertThat(result.get().getPlan()).isEqualTo("PRO");
    }
}
