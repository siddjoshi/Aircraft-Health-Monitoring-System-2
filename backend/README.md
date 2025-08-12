# Aircraft Health Monitoring System - Backend

Java Spring Boot backend for the Real-Time Aircraft Health Monitoring System.

## Features

- ✅ Real-time aircraft sensor data simulation
- ✅ Anomaly detection for critical systems
- ✅ WebSocket communication for live updates
- ✅ REST API endpoints
- ✅ Comprehensive logging and monitoring

## System Components

### Core Services

1. **DataSimulationService**: Generates realistic aircraft sensor data
2. **AnomalyDetectionService**: Detects anomalies in critical systems
3. **WebSocketService**: Handles real-time communication
4. **AircraftController**: REST API endpoints

### Aircraft Systems Monitored

- **Engine**: RPM, temperature, oil pressure, oil temperature
- **Fuel**: Level, consumption, pressure, temperature
- **Hydraulic**: Pressure, temperature, fluid level
- **Flight Data**: Altitude, airspeed, ground speed, Mach number, vertical speed
- **Additional**: Cabin pressure, battery voltage, generator output

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

```bash
# Navigate to backend directory
cd backend

# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Aircraft Data

- `GET /api/aircraft/data` - Get current aircraft sensor data
- `GET /api/aircraft/status` - Get system status
- `GET /api/aircraft/health` - Get system health

### Anomaly Simulation

- `POST /api/aircraft/simulate/engine-anomaly` - Trigger engine anomaly
- `POST /api/aircraft/simulate/fuel-anomaly` - Trigger fuel anomaly
- `POST /api/aircraft/simulate/hydraulic-anomaly` - Trigger hydraulic anomaly

### Alerts

- `POST /api/aircraft/alert` - Send custom alert with validation

#### Alert Request Format
```json
{
  "type": "ENGINE",      // Required: Uppercase letters and underscores only (2-50 chars)
  "message": "Alert message",  // Required: 5-500 characters
  "severity": "WARNING"   // Required: INFO, WARNING, or CRITICAL
}
```

#### Security Features
- **Input Validation**: All inputs are validated using Bean Validation
- **Input Sanitization**: XSS and injection attack prevention
- **Content Security**: Malicious content detection and blocking
- **Error Handling**: Comprehensive validation error responses

## Security

The Aircraft Health Monitoring System implements comprehensive security measures to protect against common web vulnerabilities:

### Input Validation
- **Bean Validation**: All user inputs are validated using Jakarta Bean Validation annotations
- **Type Safety**: Strong typing with dedicated DTOs instead of generic Map objects
- **Length Constraints**: Configurable min/max length validation for all text fields
- **Pattern Matching**: Regex validation for alert types and severity levels

### Input Sanitization
- **XSS Prevention**: Automatic removal of HTML tags and script content
- **Injection Protection**: Detection and blocking of malicious patterns
- **Content Filtering**: Removal of potentially dangerous characters and sequences
- **Safe Defaults**: Fallback to safe values for invalid inputs

### Security Validation
- **Malicious Content Detection**: Advanced pattern matching for injection attempts
- **Real-time Blocking**: Immediate rejection of dangerous content with 403 responses
- **Security Logging**: Comprehensive logging of security incidents for monitoring

### Error Handling
- **Global Exception Handler**: Centralized validation error processing
- **Structured Responses**: Consistent error response format with detailed field-level errors
- **Security-conscious Messages**: Non-revealing error messages to prevent information disclosure

## WebSocket

- **Endpoint**: `ws://localhost:8080/websocket`
- **SockJS**: `http://localhost:8080/websocket`

### Message Types

1. **aircraft_data**: Real-time sensor data
2. **alert**: System alerts and warnings
3. **connection**: Connection status messages

## Anomaly Detection

The system monitors for:

### Engine Anomalies
- RPM outside normal range (500-3000)
- Engine temperature > 200°C
- Oil pressure outside range (20-100 PSI)
- Oil temperature > 120°C

### Fuel Anomalies
- Fuel level < 20%
- Fuel consumption > 1000 GPH
- Fuel pressure outside range (10-50 PSI)

### Hydraulic Anomalies
- Hydraulic pressure < 2000 PSI or > 3500 PSI
- Hydraulic temperature > 80°C
- Hydraulic fluid level < 80%

### Flight Data Anomalies
- Altitude > 45,000 feet
- Airspeed > 600 knots
- Mach number > 0.9
- Vertical speed > 5000 ft/min

## Configuration

Key configuration options in `application.properties`:

- `server.port`: Server port (default: 8080)
- `logging.level.com.aircraft.monitoring`: Logging level
- `spring.websocket.max-text-message-size`: WebSocket message size limit

## Development

### Project Structure

```
src/main/java/com/aircraft/monitoring/
├── AircraftMonitoringApplication.java    # Main application class
├── config/
│   └── WebSocketConfig.java            # WebSocket configuration
├── controller/
│   └── AircraftController.java         # REST API controller
├── model/
│   └── AircraftData.java              # Aircraft data model
└── service/
    ├── AnomalyDetectionService.java    # Anomaly detection logic
    ├── DataSimulationService.java      # Data simulation
    └── WebSocketService.java          # WebSocket handling
```

### Adding New Features

1. Create new service classes in the `service` package
2. Add REST endpoints in `AircraftController`
3. Update `AircraftData` model if needed
4. Add WebSocket message types in `WebSocketService`

## Testing

```bash
# Run unit tests
mvn test

# Run integration tests
mvn verify
```

## Monitoring

- Health check: `GET /actuator/health`
- Metrics: `GET /actuator/metrics`
- Application info: `GET /actuator/info` 