package com.tailorcvpro.backend.tailoring.model;

import com.tailorcvpro.backend.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "tailoring_tailoringsession")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public  class TailoringSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "session_type")
    private String sessionType;

    @Column(name = "job_description", columnDefinition = "TEXT")
    private String jobDescription;

    @Column(name = "original_resume", columnDefinition = "TEXT")
    private String originalResume;

    @Column(columnDefinition = "TEXT")
    private String result;

    @Column(name = "tokens_used")
    private Integer tokensUsed;

    @Column(name = "created_at", nullable = false,  updatable = false)
    private LocalDateTime createdAt;
}