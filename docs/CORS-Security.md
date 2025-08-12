# CORS Security Configuration Guide

## Overview

This document describes the CORS (Cross-Origin Resource Sharing) security configuration implemented in the Aircraft Health Monitoring System to replace the previous insecure wildcard policy.

## Security Issues Addressed

### Previous Vulnerabilities
- `@CrossOrigin(origins = "*")` in AircraftController.java allowed ANY domain access
- `.setAllowedOriginPatterns("*")` in WebSocketConfig.java allowed ANY WebSocket origin  
- `spring.web.cors.allowed-origins=*` in application.properties was globally permissive

### Security Risks Mitigated
- **Cross-Origin Attacks**: Malicious websites can no longer access the API
- **Data Theft**: Unauthorized domains cannot steal aircraft data
- **CSRF Attacks**: Cross-site request forgery protection enabled
- **Production Safety**: System is now safe for production deployment

## Current Secure Configuration

### CorsConfig.java
Central CORS configuration class that implements `WebMvcConfigurer`:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // Environment-specific allowed origins
    @Value("${cors.allowed-origins:http://localhost:3000,http://localhost:3001}")
    private String allowedOrigins;
    
    // Restricted HTTP methods
    @Value("${cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS}")
    private String allowedMethods;
    
    // Limited headers
    @Value("${cors.allowed-headers:Content-Type,Authorization,X-Requested-With}")
    private String allowedHeaders;
}
```

### Environment-Specific Configuration

#### Development (application-dev.properties)
```properties
cors.allowed-origins=http://localhost:3000,http://localhost:3001,http://127.0.0.1:3000,http://0.0.0.0:3000
cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
cors.allowed-headers=Content-Type,Authorization,X-Requested-With
cors.allow-credentials=true
cors.max-age=3600
```

#### Production (application-prod.properties)
```properties
cors.allowed-origins=https://yourdomain.com,https://app.yourdomain.com
cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
cors.allowed-headers=Content-Type,Authorization,X-Requested-With
cors.allow-credentials=true
cors.max-age=3600
```

## Configuration Properties

| Property | Description | Default | Security Impact |
|----------|-------------|---------|-----------------|
| `cors.allowed-origins` | Comma-separated list of allowed origins | localhost:3000,3001 | **CRITICAL** - Controls domain access |
| `cors.allowed-methods` | Allowed HTTP methods | GET,POST,PUT,DELETE,OPTIONS | Limits API method access |
| `cors.allowed-headers` | Allowed request headers | Content-Type,Authorization,X-Requested-With | Controls header access |
| `cors.allow-credentials` | Allow credentials in requests | true | Enables authenticated requests |
| `cors.max-age` | Preflight cache duration (seconds) | 3600 | Performance optimization |

## Deployment Instructions

### Development Environment
```bash
# Use development profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Production Environment
```bash
# Use production profile with your domains
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Custom Origins
```bash
# Override allowed origins
mvn spring-boot:run -Dcors.allowed-origins=https://your-custom-domain.com
```

## Testing CORS Configuration

### Valid Origin Test
```bash
curl -H "Origin: http://localhost:3000" \
     -H "Access-Control-Request-Method: GET" \
     -X OPTIONS http://localhost:8080/api/aircraft/health
```
**Expected**: HTTP 200 with CORS headers

### Invalid Origin Test  
```bash
curl -H "Origin: http://malicious-site.com" \
     -H "Access-Control-Request-Method: GET" \
     -X OPTIONS http://localhost:8080/api/aircraft/health
```
**Expected**: HTTP 403 "Invalid CORS request"

## WebSocket Security

WebSocket configuration also uses environment-specific origins:

```java
registry.addHandler(webSocketService, "/websocket")
        .setAllowedOrigins(corsConfig.getAllowedOriginsArray())
        .withSockJS();
```

## Frontend Integration

### React Example
```javascript
// This will work from allowed origins only
fetch('http://localhost:8080/api/aircraft/data', {
    method: 'GET',
    credentials: 'include', // Enabled by allow-credentials
    headers: {
        'Content-Type': 'application/json'
    }
});
```

### WebSocket Example
```javascript
// WebSocket connection from allowed origins
const socket = new SockJS('http://localhost:8080/websocket');
```

## Monitoring and Troubleshooting

### Common Issues

1. **403 Invalid CORS request**
   - Check if your domain is in `cors.allowed-origins`
   - Verify you're using the correct protocol (http/https)

2. **Missing CORS headers**
   - Ensure `CorsConfig` is being loaded
   - Check Spring Boot profile is set correctly

3. **WebSocket connection refused**
   - Verify WebSocket origin is in allowed list
   - Check SockJS fallback configuration

### Debug Logging
Enable CORS debug logging:
```properties
logging.level.org.springframework.web.cors=DEBUG
```

## Security Best Practices

1. **Never use wildcards** (`*`) in production
2. **Use HTTPS** in production for all origins
3. **Limit allowed headers** to only what's needed
4. **Set appropriate max-age** for preflight caching
5. **Regularly audit** allowed origins list
6. **Use environment-specific** configurations

## Compliance

This configuration addresses:
- **OWASP CORS Security Guidelines**
- **Production Security Standards**
- **Cross-Origin Attack Prevention**
- **Data Protection Requirements**