package com.tailorcvpro.backend.subscription.controller;

import tools.jackson.databind.ObjectMapper;
import com.tailorcvpro.backend.subscription.dto.SubscriptionResponseDto;
import com.tailorcvpro.backend.subscription.dto.WebhookRequestDto;
import com.tailorcvpro.backend.subscription.service.SubscriptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebhookController.class)
@WithMockUser
class WebhookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SubscriptionService subscriptionService;

    @Test
    @DisplayName("Should process webhook POST payload successfully and return updated subscription")
    void shouldHandleWebhookSuccessfully() throws Exception {
        // GIVEN
        LocalDateTime periodEnd = LocalDateTime.now().plusMonths(1);
        WebhookRequestDto requestDto = new WebhookRequestDto("sub_123456", "PRO", periodEnd);

        SubscriptionResponseDto responseDto = new SubscriptionResponseDto(
                10L, 1L, "PRO", "cust_123", "sub_123456", periodEnd, LocalDateTime.now()
        );

        when(subscriptionService.handleWebhookUpdate(eq("sub_123456"), eq("PRO"), any(LocalDateTime.class)))
                .thenReturn(responseDto);

        // WHEN & THEN
        mockMvc.perform(post("/api/subscriptions/webhook")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plan").value("PRO"))
                .andExpect(jsonPath("$.lemonSqueezySubscriptionId").value("sub_123456"));
    }
}
