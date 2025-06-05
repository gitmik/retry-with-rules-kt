# Retry Spring Boot Integration

Spring Boot framework integration for the retry mechanism.

## Features

- Spring AOP-based retry mechanism
- Automatic retry rule application
- Dependency injection support
- Configuration through annotations

## Usage

### Configuration

```kotlin
@Configuration
@EnableAspectJAutoProxy
class RetryConfiguration(private val enumClass: Class<out Enum<*>>) {
    @Bean
    fun <T : Enum<T>> retryMechanism(): RetryMechanism<T> = RetryMechanism()

    @Bean
    fun <T : Enum<T>> retryAspect(retryMechanism: RetryMechanism<T>): RetryAspect<T> = RetryAspect(retryMechanism, enumClass.kotlin as kotlin.reflect.KClass<T>)

    @Bean
    fun <T : Enum<T>> springRetryAspect(retryAspect: RetryAspect<T>): SpringRetryAspect<T> = SpringRetryAspect(retryAspect)
}
```

### Using in Services

```kotlin
@Service
class PaymentService {
    @RetryWithRules(attempts = 3, category = BusinessScenario::class)
    fun processPayment(request: PaymentRequest): String {
        // Implementation
    }
}
```

## Components

- `SpringRetryAspect`: Spring Boot aspect for retry functionality
- `RetryConfiguration`: Configuration class for Spring Boot integration

## Dependencies

- retry-core
- Spring Boot Starter AOP

## Example

See the [retry-spring-example](../retry-spring-example/README.md) module for a complete example application. 