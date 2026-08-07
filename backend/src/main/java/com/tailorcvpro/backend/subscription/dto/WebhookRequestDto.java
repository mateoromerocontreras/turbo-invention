package com.tailorcvpro.backend.subscription.dto;

import java.time.LocalDateTime;

public record WebhookRequestDto(
        String lemonSqueezySubscriptionId,
        String newPlan,
        LocalDateTime periodEnd
){
}
