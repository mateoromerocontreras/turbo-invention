package com.tailorcvpro.backend.tailoring.service;

import org.springframework.stereotype.Service;

@Service
public class MockLlmPromptServiceImpl implements LlmPromptService {

    @Override
    public String generateTailoredContent(String sessionType, String jobDescription, String originalResume) {

        return sessionType + "\n" + jobDescription + "\n" + originalResume;
    }
}
