# Retry Framework

A flexible and extensible retry mechanism framework for Java and Kotlin projects, with support for Spring Boot and Micronaut.

## Features

- Core retry mechanism with customizable rules
- Spring Boot integration with AOP support
- Micronaut integration with AOP support
- Type-safe retry categories using enums
- Configurable retry attempts and rules
- Support for custom retry rules
- Example applications for both Spring Boot and Micronaut

## Modules

- `retry-core`: Core retry mechanism implementation
- `retry-spring`: Spring Boot integration
- `retry-micronaut`: Micronaut integration
- `retry-spring-example`: Example Spring Boot application
- `retry-micronaut-example`: Example Micronaut application

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Building

```bash
mvn clean install
```

### Usage

#### Spring Boot

1. Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>retry-spring</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

2. Create a retry category enum:

```kotlin
enum class MyRetryCategory {
    MY_OPERATION
}
```

3. Configure the retry mechanism:

```kotlin
@Configuration
class RetryConfig {
    @Bean
    fun retryMechanism(): RetryMechanism<MyRetryCategory> {
        return RetryMechanism<MyRetryCategory>().apply {
            registerGroup(createRetryGroup())
        }
    }

    @Bean
    fun retryAspect(retryMechanism: RetryMechanism<MyRetryCategory>): RetryAspect<MyRetryCategory> {
        return RetryAspect(retryMechanism, MyRetryCategory::class)
    }

    private fun createRetryGroup(): RetryGroup<MyRetryCategory> {
        return RetryGroupBuilder<MyRetryCategory, String, Result, Exception>(MyRetryCategory.MY_OPERATION)
            .addRule(object : RetryRule<String, Result, Exception> {
                override fun shouldApply(context: RetryContext<String, Result, Exception>): Boolean {
                    return context.exception != null
                }

                override fun apply(context: RetryContext<String, Result, Exception>): String? {
                    return context.input
                }
            })
            .build()
    }
}
```

4. Use the retry mechanism in your service:

```kotlin
@Service
class MyService {
    @RetryWithRules(
        attempts = 3,
        category = MyRetryCategory::class
    )
    fun myMethod(input: String): Result {
        // Your implementation
    }
}
```

#### Micronaut

1. Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>retry-micronaut</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

2. Follow similar steps as Spring Boot, but use Micronaut's dependency injection.

## Examples

Check out the example applications in the `retry-spring-example` and `retry-micronaut-example` modules for complete working examples.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details. 