package com.tailorcvpro.backend.subscription.dto;

import com.tailorcvpro.backend.subscription.model.Subscription;

import java.time.LocalDateTime;

public record SubscriptionResponseDto(
        Long id,
        Long userId,
        String plan,
        String lemonSqueezyCustomerId,
        String lemonSqueezySubscriptionId,
        LocalDateTime currentPeriodEnd,
        LocalDateTime createdAt
) {
    public static SubscriptionResponseDto fromEntity(Subscription subscription) {
        return new SubscriptionResponseDto(
                subscription.getId(),
                subscription.getUser().getId(),
                subscription.getPlan(),
                subscription.getLemonSqueezyCustomerId(),
                subscription.getLemonSqueezySubscriptionId(),
                subscription.getCurrentPeriodEnd(),
                subscription.getCreatedAt()
        );
    }
}
