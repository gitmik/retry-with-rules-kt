# Retry Core

Core implementation of the retry mechanism with rules support.

## Features

- Type-safe retry rules with generics
- Support for multiple input arguments
- Rule groups based on enums
- Ordered rule application
- Full retry history access
- Input argument mutation
- Exception handling

## Usage

### Defining Retry Rules

```kotlin
class PaymentAmountRule : RetryRule<PaymentRequest, String, Exception> {
    override fun shouldApply(context: RetryContext<PaymentRequest, String, Exception>): Boolean {
        return context.exception != null && context.attempt < context.maxAttempts
    }

    override fun apply(context: RetryContext<PaymentRequest, String, Exception>): PaymentRequest {
        return context.input.copy(
            amount = context.input.amount * 0.95, // Reduce amount by 5% on retry
            retryCount = context.input.retryCount + 1
        )
    }
}
```

### Creating Retry Groups

```kotlin
val paymentGroup = RetryGroupBuilder<BusinessScenario, PaymentRequest, String, Exception>(BusinessScenario.PAYMENT_PROCESSING)
    .addRule(PaymentAmountRule())
    .build()
```

### Using the Retry Mechanism

```kotlin
val retryMechanism = RetryMechanism<BusinessScenario>()
retryMechanism.registerGroup(paymentGroup)

val result = retryMechanism.execute(
    category = BusinessScenario.PAYMENT_PROCESSING,
    maxAttempts = 3,
    input = request
) { input ->
    processPayment(input)
}
```

## Components

- `RetryRule<T, R, E>`: Interface for retry rules
- `RetryContext<T, R, E>`: Context object for retry attempts
- `RetryAttempt<T, R, E>`: Represents a single retry attempt
- `RetryGroup<T>`: Interface for retry rule groups
- `RetryMechanism<T>`: Main class for handling retry operations

## Dependencies

- Kotlin Standard Library
- Kotlin Reflection 