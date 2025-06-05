package com.example.library.retry.micronaut

import com.example.library.retry.RetryAspect
import com.example.library.retry.RetryMechanism
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton

/**
 * Micronaut configuration for retry functionality.
 * @param T The enum type that identifies retry groups
 */
@Factory
class RetryConfiguration<T : Enum<T>>(private val enumClass: Class<T>) {
    @Singleton
    fun retryMechanism(): RetryMechanism<T> = RetryMechanism()

    @Singleton
    fun retryAspect(retryMechanism: RetryMechanism<T>): RetryAspect<T> = RetryAspect(retryMechanism, enumClass.kotlin)
} 