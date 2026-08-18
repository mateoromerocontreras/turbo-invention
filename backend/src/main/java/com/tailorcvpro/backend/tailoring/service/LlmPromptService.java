package com.tailorcvpro.backend.tailoring.service;

public interface LlmPromptService {
    String generateTailoredContent(String sessionType, String jobDescription, String originalResume);
}
