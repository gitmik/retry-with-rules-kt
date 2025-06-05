package com.example.library.retry

/**
 * Interface for retry rules that can be applied during retry attempts.
 * @param T The type of the input arguments
 * @param R The type of the return value
 * @param E The type of exceptions that can be thrown
 */
interface RetryRule<T, R, E : Throwable> {
    /**
     * Determines if the rule should be applied based on the current attempt context.
     * @param context The current retry context
     * @return true if the rule should be applied, false otherwise
     */
    fun shouldApply(context: RetryContext<T, R, E>): Boolean

    /**
     * Applies the rule to the current retry attempt.
     * @param context The current retry context
     * @return Modified input arguments for the next retry attempt, or null if no modification is needed
     */
    fun apply(context: RetryContext<T, R, E>): T?
}

/**
 * Context object that provides information about the current retry attempt.
 * @param T The type of the input arguments
 * @param R The type of the return value
 * @param E The type of exceptions that can be thrown
 */
data class RetryContext<T, R, E : Throwable>(
    val attempt: Int,
    val maxAttempts: Int,
    val input: T,
    val result: R?,
    val exception: E?,
    val history: List<RetryAttempt<T, R, E>>
)

/**
 * Represents a single retry attempt with its input, result, and exception.
 * @param T The type of the input arguments
 * @param R The type of the return value
 * @param E The type of exceptions that can be thrown
 */
data class RetryAttempt<T, R, E : Throwable>(
    val attempt: Int,
    val input: T,
    val result: R?,
    val exception: E?
) 