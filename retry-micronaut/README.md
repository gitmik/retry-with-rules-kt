# Retry Micronaut Integration

Micronaut framework integration for the retry mechanism.

## Features

- Micronaut AOP-based retry mechanism
- Automatic retry rule application
- Dependency injection support
- Configuration through annotations

## Usage

### Configuration

```kotlin
@Factory
class RetryConfiguration<T : Enum<T>> {
    @Singleton
    fun retryMechanism(): RetryMechanism<T> = RetryMechanism()

    @Singleton
    fun retryAspect(retryMechanism: RetryMechanism<T>): RetryAspect<T> = RetryAspect(retryMechanism)
}
```

### Using in Services

```kotlin
@Singleton
class PaymentService {
    @RetryWithRules(attempts = 3, category = BusinessScenario::class)
    fun processPayment(request: PaymentRequest): String {
        // Implementation
    }
}
```

## Components

- `MicronautRetryInterceptor`: Micronaut interceptor for retry functionality
- `RetryConfiguration`: Configuration class for Micronaut integration

## Dependencies

- retry-core
- Micronaut Runtime
- Micronaut AOP

## Example

See the [retry-micronaut-example](../retry-micronaut-example/README.md) module for a complete example application. 