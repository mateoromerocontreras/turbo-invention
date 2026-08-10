package com.tailorcvpro.backend.subscription.service;

import com.tailorcvpro.backend.subscription.dto.SubscriptionResponseDto;
import com.tailorcvpro.backend.subscription.model.Subscription;
import com.tailorcvpro.backend.subscription.repository.SubscriptionRepository;
import com.tailorcvpro.backend.user.model.User;
import com.tailorcvpro.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Test
    @DisplayName("Should return subscription when valid userId is given")
    void shouldGetSubscriptionByUserIdSuccesfully() {
        // GIVEN
        User user = new User();
        user.setId(1L);

        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setUser(user);
        subscription.setPlan("PRO");
        subscription.setCreatedAt(LocalDateTime.now());

        when(subscriptionRepository.findByUserId(1L)).thenReturn(Optional.of(subscription));

        // WHEN
        SubscriptionResponseDto result = subscriptionService.getSubscriptionByUserId(1L);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.plan()).isEqualTo("PRO");
        verify(subscriptionRepository, times(1)).findByUserId(1L);
    }

    @Test
    @DisplayName("Should throw exception when getting subscription for user with no subscription")
    void shouldThrowExceptionWhenSubscriptionNotFoundForUser() {
        // TODO: Stub subscriptionRepository.findByUserId(1L) to return Optional.empty()
        when(subscriptionRepository.findByUserId(1L)).thenReturn(Optional.empty());
        // TODO: Assert that subscriptionService.getSubscriptionByUserId(1L) throws IllegalArgumentException

        assertThatThrownBy(() -> subscriptionService.getSubscriptionByUserId(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Subscription not found for userId: " + 1L);
    }

    @Test
    @DisplayName("Should handle webhook update successfully when subscription exists")
    void shouldHandleWebhookUpdateSuccessfully() {
        // GIVEN
        User user = new User();
        user.setId(1L);

        Subscription subscription = new Subscription();
        subscription.setId(10L);
        subscription.setUser(user);
        subscription.setPlan("FREE");
        subscription.setLemonSqueezySubscriptionId("sub_123456");
        subscription.setCreatedAt(LocalDateTime.now());

        when(subscriptionRepository.
                findByLemonSqueezySubscriptionId("sub_123456"))
                .thenReturn(Optional.of(subscription));
        when(subscriptionRepository.save(any(Subscription.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // WHEN
        LocalDateTime periodEnd = LocalDateTime.now().plusMonths(1);
        SubscriptionResponseDto result = subscriptionService.
                handleWebhookUpdate("sub_123456", "PRO", periodEnd);

        // THEN
        assertThat(result.plan()).isEqualTo("PRO");
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    @DisplayName("Should throw exception when webhook update receives unknown subscription id")
    void shouldThrowExceptionWhenWebhookSubscriptionNotFound() {
        // TODO: Implement negative test case for handleWebhookUpdate!
        when(subscriptionRepository.findByLemonSqueezySubscriptionId("sub_999"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> subscriptionService
                .handleWebhookUpdate("sub_999", "PRO", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Subscription not found for subscription id: sub_999");
    }
}
