package com.aircraft.monitoring.util;

import org.springframework.stereotype.Component;

/**
 * Utility class for input sanitization and security validation.
 * 
 * Provides methods to sanitize user inputs and prevent various
 * injection attacks including XSS, HTML injection, and script injection.
 * 
 * @author Aircraft Monitoring Team
 * @version 1.0.0
 */
@Component
public class InputSanitizer {
    
    /**
     * Sanitizes input text by removing potentially dangerous characters
     * and sequences that could be used for injection attacks.
     * 
     * @param input The input string to sanitize
     * @return The sanitized string, or null if input was null
     */
    public String sanitizeText(String input) {
        if (input == null) {
            return null;
        }
        
        // Remove or escape potentially dangerous characters
        String sanitized = input
            // Remove script tags and content (case insensitive)
            .replaceAll("(?i)<script[^>]*>.*?</script>", "")
            // Remove HTML tags
            .replaceAll("<[^>]*>", "")
            // Remove javascript: URLs
            .replaceAll("(?i)javascript:", "")
            // Remove potentially dangerous characters
            .replaceAll("[<>\"'&]", "")
            // Remove excessive whitespace
            .trim()
            .replaceAll("\\s+", " ");
            
        return sanitized;
    }
    
    /**
     * Sanitizes alert type to ensure it only contains valid characters.
     * 
     * @param alertType The alert type to sanitize
     * @return The sanitized alert type
     */
    public String sanitizeAlertType(String alertType) {
        if (alertType == null) {
            return null;
        }
        
        // Only allow uppercase letters and underscores
        return alertType.toUpperCase().replaceAll("[^A-Z_]", "");
    }
    
    /**
     * Sanitizes severity level to ensure it's one of the allowed values.
     * 
     * @param severity The severity level to sanitize
     * @return The sanitized severity level, defaults to "INFO" if invalid
     */
    public String sanitizeSeverity(String severity) {
        if (severity == null) {
            return "INFO";
        }
        
        String cleanSeverity = severity.toUpperCase().trim();
        
        // Only allow valid severity levels
        if ("WARNING".equals(cleanSeverity) || "CRITICAL".equals(cleanSeverity)) {
            return cleanSeverity;
        }
        
        return "INFO"; // Default to INFO for any invalid input
    }
    
    /**
     * Validates that a string doesn't contain common injection patterns.
     * 
     * @param input The input to validate
     * @return true if the input appears safe, false if potentially malicious
     */
    public boolean isInputSafe(String input) {
        if (input == null) {
            return true;
        }
        
        String lowerInput = input.toLowerCase();
        
        // Check for common injection patterns
        String[] dangerousPatterns = {
            "script", "javascript", "vbscript", "onload", "onerror",
            "document.cookie", "alert(", "eval(", "expression(",
            "iframe", "object", "embed", "form", "meta",
            "<?", "<%", "%>", "?>", "<!--", "-->"
        };
        
        for (String pattern : dangerousPatterns) {
            if (lowerInput.contains(pattern)) {
                return false;
            }
        }
        
        return true;
    }
}