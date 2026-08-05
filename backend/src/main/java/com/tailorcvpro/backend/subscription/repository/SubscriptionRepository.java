package com.tailorcvpro.backend.subscription.repository;

import com.tailorcvpro.backend.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserId(Long userId);
    Optional<Subscription> findByLemonSqueezySubscriptionId(String lemonSqueezySubscriptionId);
}
