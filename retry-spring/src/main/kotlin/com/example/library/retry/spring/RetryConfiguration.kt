package com.example.library.retry.spring

import com.example.library.retry.RetryAspect
import com.example.library.retry.RetryMechanism
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

/**
 * Spring Boot configuration for retry functionality.
 * @param T The enum type that identifies retry groups
 */
@Configuration
@EnableAspectJAutoProxy
class RetryConfiguration<T : Enum<T>> {
    @Bean
    fun retryMechanism(): RetryMechanism<T> = RetryMechanism()

    @Bean
    fun retryAspect(retryMechanism: RetryMechanism<T>): RetryAspect<T> = RetryAspect(retryMechanism)

    @Bean
    fun springRetryAspect(retryAspect: RetryAspect<T>): SpringRetryAspect<T> = SpringRetryAspect(retryAspect)
} 