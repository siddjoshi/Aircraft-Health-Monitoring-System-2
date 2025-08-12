package com.aircraft.monitoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * CORS security configuration for aircraft monitoring system.
 * 
 * This configuration provides secure CORS settings by:
 * - Using environment-specific allowed origins
 * - Restricting allowed methods and headers
 * - Enabling credentials for authenticated requests
 * - Implementing proper pre-flight request validation
 * 
 * @author Aircraft Monitoring Team
 * @version 1.0.0
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins:http://localhost:3000,http://localhost:3001}")
    private String allowedOrigins;

    @Value("${cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS}")
    private String allowedMethods;

    @Value("${cors.allowed-headers:Content-Type,Authorization,X-Requested-With}")
    private String allowedHeaders;

    @Value("${cors.allow-credentials:true}")
    private boolean allowCredentials;

    @Value("${cors.max-age:3600}")
    private long maxAge;

    /**
     * Configure CORS settings for all endpoints
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Parse allowed origins from comma-separated string
        String[] origins = allowedOrigins.split(",");
        
        // Parse allowed methods from comma-separated string
        String[] methods = allowedMethods.split(",");
        
        // Parse allowed headers from comma-separated string
        String[] headers = allowedHeaders.split(",");
        
        registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowedMethods(methods)
                .allowedHeaders(headers)
                .allowCredentials(allowCredentials)
                .maxAge(maxAge);
    }

    /**
     * Gets the list of allowed origins for WebSocket configuration
     * 
     * @return Array of allowed origins
     */
    public String[] getAllowedOriginsArray() {
        return allowedOrigins.split(",");
    }
}