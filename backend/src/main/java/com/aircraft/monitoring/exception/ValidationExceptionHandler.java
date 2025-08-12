package com.aircraft.monitoring.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Global exception handler for validation and security-related errors.
 * 
 * This handler catches validation exceptions thrown by Bean Validation
 * and returns user-friendly error responses while logging security incidents.
 * 
 * @author Aircraft Monitoring Team
 * @version 1.0.0
 */
@ControllerAdvice
@Slf4j
public class ValidationExceptionHandler {
    
    /**
     * Handles Bean Validation errors from @Valid annotations.
     * 
     * @param ex The validation exception
     * @return A structured error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        response.put("status", "error");
        response.put("message", "Validation failed");
        response.put("errors", errors);
        response.put("timestamp", System.currentTimeMillis());
        
        // Log security incident
        log.warn("Validation failed for request. Errors: {}", errors);
        
        return ResponseEntity.badRequest().body(response);
    }
    
    /**
     * Handles constraint violation exceptions.
     * 
     * @param ex The constraint violation exception
     * @return A structured error response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolations(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }
        
        response.put("status", "error");
        response.put("message", "Constraint validation failed");
        response.put("errors", errors);
        response.put("timestamp", System.currentTimeMillis());
        
        // Log security incident
        log.warn("Constraint validation failed. Violations: {}", errors);
        
        return ResponseEntity.badRequest().body(response);
    }
    
    /**
     * Handles binding exceptions.
     * 
     * @param ex The binding exception
     * @return A structured error response
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handleBindingErrors(BindException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        response.put("status", "error");
        response.put("message", "Request binding failed");
        response.put("errors", errors);
        response.put("timestamp", System.currentTimeMillis());
        
        // Log security incident
        log.warn("Request binding failed. Errors: {}", errors);
        
        return ResponseEntity.badRequest().body(response);
    }
    
    /**
     * Handles security-related exceptions.
     * 
     * @param ex The security exception
     * @return A structured error response
     */
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, Object>> handleSecurityException(SecurityException ex) {
        Map<String, Object> response = new HashMap<>();
        
        response.put("status", "error");
        response.put("message", "Security validation failed");
        response.put("timestamp", System.currentTimeMillis());
        
        // Log security incident
        log.error("Security validation failed: {}", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
    
    /**
     * Handles illegal argument exceptions that may indicate injection attempts.
     * 
     * @param ex The illegal argument exception
     * @return A structured error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        
        response.put("status", "error");
        response.put("message", "Invalid request parameters");
        response.put("timestamp", System.currentTimeMillis());
        
        // Log potential security incident
        log.warn("Invalid request parameters detected: {}", ex.getMessage());
        
        return ResponseEntity.badRequest().body(response);
    }
}