package com.tailorcvpro.backend.subscription.service;

import com.tailorcvpro.backend.subscription.dto.SubscriptionResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionService {
    // SubscriptionResponseDto subscribe();
    // SubscriptionResponseDto unsubscribe();
    // SubscriptionResponseDto getSubscriptionBySubscriptionId(Long subscriptionId);
    // SubscriptionResponseDto getSubscriptionByPlan(String plan);
    SubscriptionResponseDto getSubscriptionByUserId(Long userId);
    // List<SubscriptionResponseDto> getAllSubscriptions();
    // void deleteSubscription(Long subscriptionId);
    SubscriptionResponseDto createOrUpdateSubscription(
            Long userId,
            String plan,
            String customerId,
            String subscriptionId,
            LocalDateTime periodEnd
    );

    SubscriptionResponseDto handleWebhookUpdate(
            String lemonSqueezySubscriptionId,
            String newPlan,
            LocalDateTime periodEnd
    );

}
