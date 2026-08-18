package com.tailorcvpro.backend.tailoring.controller;

import com.tailorcvpro.backend.tailoring.dto.TailoringSessionRequestDto;
import com.tailorcvpro.backend.tailoring.dto.TailoringSessionResponseDto;
import com.tailorcvpro.backend.tailoring.service.TailoringSessionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TailoringSessionController.class)
@WithMockUser
public class TailoringSessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TailoringSessionService tailoringSessionService;

    @Test
    @DisplayName("Should create tailoring session via POST /api/tailoring-sessions")
    public void createTailoringSessionViaPOST() throws Exception {
        // GIVEN
        TailoringSessionResponseDto responseDto = new TailoringSessionResponseDto(
                1L,
                10L,
                "Session type",
                "Developer",
                "Original Resume",
                "New Resume",
                5,
                LocalDateTime.now()
        );

        TailoringSessionRequestDto requestDto = new TailoringSessionRequestDto(
                10L,
                "Session type",
                "Developer",
                "Original Resume"
        );

        when(tailoringSessionService.createTailoringSession(requestDto)).thenReturn(responseDto);

        // WHEN & THEN
        mockMvc.perform(post("/api/tailoring-sessions")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.result").value("New Resume"));
    }
}
