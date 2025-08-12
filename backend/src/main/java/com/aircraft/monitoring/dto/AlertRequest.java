package com.aircraft.monitoring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object for alert requests with comprehensive validation.
 * 
 * This DTO ensures all alert data is properly validated and sanitized
 * before being processed by the system, preventing injection attacks
 * and ensuring data integrity.
 * 
 * @author Aircraft Monitoring Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertRequest {
    
    /**
     * Alert type - must be one of the predefined system types
     */
    @NotBlank(message = "Alert type is required")
    @Size(min = 2, max = 50, message = "Alert type must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Z_]+$", message = "Alert type must contain only uppercase letters and underscores")
    private String type;
    
    /**
     * Alert message - the main content of the alert
     */
    @NotBlank(message = "Alert message is required")
    @Size(min = 5, max = 500, message = "Alert message must be between 5 and 500 characters")
    private String message;
    
    /**
     * Alert severity level - must be one of INFO, WARNING, or CRITICAL
     */
    @NotNull(message = "Alert severity is required")
    @Pattern(regexp = "^(INFO|WARNING|CRITICAL)$", message = "Alert severity must be INFO, WARNING, or CRITICAL")
    private String severity;
    
    /**
     * Default constructor that sets default severity to INFO
     */
    public AlertRequest(String type, String message) {
        this.type = type;
        this.message = message;
        this.severity = "INFO";
    }
}