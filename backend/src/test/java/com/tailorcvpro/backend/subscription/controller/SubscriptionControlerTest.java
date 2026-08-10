package com.tailorcvpro.backend.subscription.controller;

import com.tailorcvpro.backend.subscription.dto.SubscriptionResponseDto;
import com.tailorcvpro.backend.subscription.service.SubscriptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubscriptionController.class)
@WithMockUser
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubscriptionService subscriptionService;

    @Test
    @DisplayName("Should return 200 OK and subscription payload when valid user id is provided")
    void shouldReturnSubscriptionByUserId() throws Exception {
        // GIVEN
        SubscriptionResponseDto dto = new SubscriptionResponseDto(
                10L, 1L, "PRO", "cust_123", "sub_123",
                LocalDateTime.now().plusMonths(1), LocalDateTime.now()
        );

        when(subscriptionService.getSubscriptionByUserId(1L)).thenReturn(dto);

        // WHEN & THEN
        mockMvc.perform(get("/api/subscriptions/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plan").value("PRO"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.lemonSqueezyCustomerId").value("cust_123"));
    }
}
