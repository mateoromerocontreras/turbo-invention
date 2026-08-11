package com.tailorcvpro.backend.tailoring.repository;

import com.tailorcvpro.backend.tailoring.model.TailoringSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TailoringSessionRepository extends JpaRepository<TailoringSession, Long> {
    List<TailoringSession> findByUserId(Long userId);
    List<TailoringSession> findByUserIdOrderByCreatedAtDesc(Long userId);
}
