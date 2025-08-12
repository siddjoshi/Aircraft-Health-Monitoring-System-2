package com.aircraft.monitoring.config;

import com.aircraft.monitoring.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket configuration for real-time aircraft data communication.
 * 
 * This configuration sets up WebSocket endpoints and handlers for
 * broadcasting aircraft sensor data to connected clients with secure CORS settings.
 * 
 * @author Aircraft Monitoring Team
 * @version 1.0.0
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    
    private final WebSocketService webSocketService;
    private final CorsConfig corsConfig;
    
    public WebSocketConfig(WebSocketService webSocketService, @Autowired CorsConfig corsConfig) {
        this.webSocketService = webSocketService;
        this.corsConfig = corsConfig;
    }
    
    /**
     * Registers WebSocket handlers and endpoints with secure CORS configuration
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketService, "/websocket")
                .setAllowedOrigins(corsConfig.getAllowedOriginsArray()) // Use environment-specific origins
                .withSockJS(); // Enable SockJS fallback for older browsers
    }
} 