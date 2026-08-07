package com.tailorcvpro.backend.subscription.controller;

import com.tailorcvpro.backend.subscription.dto.SubscriptionResponseDto;
import com.tailorcvpro.backend.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<SubscriptionResponseDto> getSubscriptionByUserId(@PathVariable Long userId) {
        SubscriptionResponseDto subscription = subscriptionService.getSubscriptionByUserId(userId);
        return ResponseEntity.ok(subscription);
    }
}
