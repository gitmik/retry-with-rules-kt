# Retry Micronaut Example

Example application demonstrating the usage of the retry mechanism with Micronaut.

## Features

- REST API for payment processing
- Retry mechanism integration
- Simulated payment processing with retries
- Example retry rules

## Running the Application

```bash
mvn mn:run
```

## Testing the Application

### Test with Low Amount (Will Retry)

```bash
curl -X POST http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{"amount": 90.0, "currency": "USD"}'
```

### Test with Sufficient Amount (Will Succeed)

```bash
curl -X POST http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{"amount": 150.0, "currency": "USD"}'
```

## Project Structure

- `Application.kt`: Main application class
- `domain/`: Domain models
  - `BusinessScenario.kt`: Business scenario enum
  - `PaymentRequest.kt`: Payment request data class
- `service/`: Business logic
  - `PaymentService.kt`: Payment processing service with retry
- `controller/`: REST API
  - `PaymentController.kt`: Payment processing endpoint

## Dependencies

- retry-micronaut
- Micronaut HTTP Server Netty
- Micronaut Kotlin Runtime
- Logback Classic 