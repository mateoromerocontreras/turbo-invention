package com.tailorcvpro.backend.subscription.service;

import com.tailorcvpro.backend.subscription.dto.SubscriptionResponseDto;
import com.tailorcvpro.backend.subscription.model.Subscription;
import com.tailorcvpro.backend.subscription.repository.SubscriptionRepository;
import com.tailorcvpro.backend.user.model.User;
import com.tailorcvpro.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Override
    public SubscriptionResponseDto getSubscriptionByUserId(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return SubscriptionResponseDto.fromEntity(subscription);
    }

    @Override
    @Transactional
    public SubscriptionResponseDto createOrUpdateSubscription(Long userId,
                                                              String plan,
                                                              String customerId,
                                                              String subscriptionId,
                                                              LocalDateTime periodEnd) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Subscription sub  = new Subscription();
                    sub.setUser(user);
                    sub.setCreatedAt(LocalDateTime.now());
                    return sub;
                });
        subscription.setPlan(plan);
        subscription.setLemonSqueezyCustomerId(customerId);
        subscription.setCurrentPeriodEnd(periodEnd);
        subscription.setUpdatedAt(LocalDateTime.now());

        Subscription saved = subscriptionRepository.save(subscription);
        return SubscriptionResponseDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public SubscriptionResponseDto handleWebhookUpdate(String lemonSqueezySubscriptionId,
                                                       String newPlan,
                                                       LocalDateTime periodEnd) {
        Subscription subscription = subscriptionRepository.findByLemonSqueezySubscriptionId(lemonSqueezySubscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
        subscription.setPlan(newPlan);
        subscription.setCurrentPeriodEnd(periodEnd);
        subscription.setUpdatedAt(LocalDateTime.now());
        Subscription saved = subscriptionRepository.save(subscription);

        return SubscriptionResponseDto.fromEntity(saved);
    }
}
