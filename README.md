# Retry Framework

A flexible and extensible retry mechanism framework for Java and Kotlin projects, with support for multiple frameworks.

## Project Structure

The project is organized into the following modules:

- `retry-core`: Core retry mechanism implementation
- `retry-micronaut`: Micronaut framework integration
- `retry-spring`: Spring Boot framework integration
- `retry-micronaut-example`: Example application using Micronaut
- `retry-spring-example`: Example application using Spring Boot

## Features

- Retry mechanism with support for retry rules
- Rule groups based on enums for domain-specific scenarios
- Support for input argument mutation during retries
- Full retry history access
- Framework integrations (Micronaut, Spring Boot)
- Annotation-based configuration
- Type-safe rule implementation

## Building

To build the project, run:

```bash
mvn clean install
```

## Usage

### Core Module

```kotlin
// Define a retry group
val paymentGroup = RetryGroupBuilder<BusinessScenario, PaymentRequest, String, Exception>(BusinessScenario.PAYMENT_PROCESSING)
    .addRule(PaymentAmountRule())
    .build()

// Register the group
retryMechanism.registerGroup(paymentGroup)

// Use with annotation
@RetryWithRules(attempts = 3, category = BusinessScenario::class)
fun processPayment(request: PaymentRequest): String {
    // Implementation
}
```

### Framework Integrations

See the respective example modules for detailed usage:
- [Micronaut Example](retry-micronaut-example/README.md)
- [Spring Boot Example](retry-spring-example/README.md)

## Requirements

- Java 11 or higher
- Maven 3.6 or higher
- Kotlin 1.9.22

## License

This project is licensed under the MIT License - see the LICENSE file for details. 