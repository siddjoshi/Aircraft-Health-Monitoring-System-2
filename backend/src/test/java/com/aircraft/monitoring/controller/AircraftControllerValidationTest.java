package com.aircraft.monitoring.controller;

import com.aircraft.monitoring.dto.AlertRequest;
import com.aircraft.monitoring.service.DataSimulationService;
import com.aircraft.monitoring.service.WebSocketService;
import com.aircraft.monitoring.util.InputSanitizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test class for input validation and sanitization in AircraftController.
 * 
 * Tests cover:
 * - Bean validation for AlertRequest
 * - Input sanitization security
 * - Malicious input rejection
 * - Proper error responses
 * 
 * @author Aircraft Monitoring Team
 * @version 1.0.0
 */
@WebMvcTest(AircraftController.class)
class AircraftControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DataSimulationService dataSimulationService;

    @MockBean
    private WebSocketService webSocketService;

    @MockBean
    private InputSanitizer inputSanitizer;

    /**
     * Test valid alert request
     */
    @Test
    void sendAlert_ValidRequest_ShouldSucceed() throws Exception {
        AlertRequest validRequest = new AlertRequest("ENGINE", "Engine temperature high", "WARNING");
        
        // Mock the sanitizer to return safe inputs
        when(inputSanitizer.isInputSafe(any(String.class))).thenReturn(true);
        when(inputSanitizer.sanitizeAlertType(any(String.class))).thenReturn("ENGINE");
        when(inputSanitizer.sanitizeText(any(String.class))).thenReturn("Engine temperature high");
        when(inputSanitizer.sanitizeSeverity(any(String.class))).thenReturn("WARNING");

        mockMvc.perform(post("/api/aircraft/alert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Alert sent successfully"));
    }

    /**
     * Test validation error when alert type is null
     */
    @Test
    void sendAlert_NullType_ShouldReturnValidationError() throws Exception {
        AlertRequest invalidRequest = new AlertRequest(null, "Test message", "INFO");

        mockMvc.perform(post("/api/aircraft/alert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.type").exists());
    }

    /**
     * Test validation error when alert type is empty
     */
    @Test
    void sendAlert_EmptyType_ShouldReturnValidationError() throws Exception {
        AlertRequest invalidRequest = new AlertRequest("", "Test message", "INFO");

        mockMvc.perform(post("/api/aircraft/alert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.type").exists());
    }

    /**
     * Test validation error when alert type contains invalid characters
     */
    @Test
    void sendAlert_InvalidTypeFormat_ShouldReturnValidationError() throws Exception {
        AlertRequest invalidRequest = new AlertRequest("engine123", "Test message", "INFO");

        mockMvc.perform(post("/api/aircraft/alert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.type").exists());
    }

    /**
     * Test validation error when message is null
     */
    @Test
    void sendAlert_NullMessage_ShouldReturnValidationError() throws Exception {
        AlertRequest invalidRequest = new AlertRequest("ENGINE", null, "INFO");

        mockMvc.perform(post("/api/aircraft/alert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.message").exists());
    }

    /**
     * Test validation error when message is too short
     */
    @Test
    void sendAlert_MessageTooShort_ShouldReturnValidationError() throws Exception {
        AlertRequest invalidRequest = new AlertRequest("ENGINE", "Hi", "INFO");

        mockMvc.perform(post("/api/aircraft/alert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.message").exists());
    }

    /**
     * Test validation error when message is too long
     */
    @Test
    void sendAlert_MessageTooLong_ShouldReturnValidationError() throws Exception {
        String longMessage = "A".repeat(501); // Exceeds max length of 500
        AlertRequest invalidRequest = new AlertRequest("ENGINE", longMessage, "INFO");

        mockMvc.perform(post("/api/aircraft/alert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.message").exists());
    }

    /**
     * Test validation error when severity is invalid
     */
    @Test
    void sendAlert_InvalidSeverity_ShouldReturnValidationError() throws Exception {
        AlertRequest invalidRequest = new AlertRequest("ENGINE", "Test message", "INVALID");

        mockMvc.perform(post("/api/aircraft/alert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.severity").exists());
    }

    /**
     * Test validation with multiple errors
     */
    @Test
    void sendAlert_MultipleValidationErrors_ShouldReturnAllErrors() throws Exception {
        AlertRequest invalidRequest = new AlertRequest("", "Hi", "INVALID");

        mockMvc.perform(post("/api/aircraft/alert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.type").exists())
                .andExpect(jsonPath("$.errors.message").exists())
                .andExpect(jsonPath("$.errors.severity").exists());
    }

    /**
     * Test that malformed JSON is rejected
     */
    @Test
    void sendAlert_MalformedJson_ShouldReturnBadRequest() throws Exception {
        String malformedJson = "{\"type\": \"ENGINE\", \"message\": \"Test\", \"severity\": }";

        mockMvc.perform(post("/api/aircraft/alert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedJson))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test that empty request body is rejected
     */
    @Test
    void sendAlert_EmptyBody_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/aircraft/alert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }
}