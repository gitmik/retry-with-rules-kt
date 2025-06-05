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
class RetryConfiguration(private val enumClass: Class<out Enum<*>>) {
    @Bean
    fun <T : Enum<T>> retryMechanism(): RetryMechanism<T> = RetryMechanism()

    @Bean
    fun <T : Enum<T>> retryAspect(retryMechanism: RetryMechanism<T>): RetryAspect<T> = RetryAspect(retryMechanism, enumClass.kotlin as kotlin.reflect.KClass<T>)

    @Bean
    fun <T : Enum<T>> springRetryAspect(retryAspect: RetryAspect<T>): SpringRetryAspect<T> = SpringRetryAspect(retryAspect)
} 