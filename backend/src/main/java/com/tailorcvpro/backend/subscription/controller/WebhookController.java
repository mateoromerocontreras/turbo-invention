package com.tailorcvpro.backend.subscription.controller;

import com.tailorcvpro.backend.subscription.dto.SubscriptionResponseDto;
import com.tailorcvpro.backend.subscription.dto.WebhookRequestDto;
import com.tailorcvpro.backend.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponseDto> handleWebhook(@RequestBody WebhookRequestDto payload) {
        SubscriptionResponseDto updateSubscription = subscriptionService.handleWebhookUpdate(
                payload.lemonSqueezySubscriptionId(),
                payload.newPlan(),
                payload.periodEnd()
        );
        return ResponseEntity.ok(updateSubscription);
    }
}
