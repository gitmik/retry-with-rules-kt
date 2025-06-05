package com.example.library.retry

import kotlin.reflect.KClass

/**
 * Annotation to mark methods that should be retried with specific rules.
 * @param attempts Maximum number of retry attempts
 * @param category The retry group category to use
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RetryWithRules(
    val attempts: Int = 3,
    val category: KClass<out Enum<*>>
)

/**
 * Aspect implementation for the RetryWithRules annotation.
 * @param T The enum type that identifies retry groups
 */
class RetryAspect<T : Enum<T>>(
    private val retryMechanism: RetryMechanism<T>,
    private val enumClass: KClass<T>
) {
    /**
     * Intercepts method calls annotated with RetryWithRules.
     * @param I The type of the input arguments
     * @param R The type of the return value
     * @param E The type of the exception
     * @param annotation The RetryWithRules annotation
     * @param input The input arguments
     * @param function The function to execute
     * @return The result of the function execution
     * @throws Exception if all retry attempts fail
     */
    @Suppress("UNCHECKED_CAST")
    fun <I, R, E : Throwable> intercept(
        annotation: RetryWithRules,
        input: I,
        function: (I) -> R
    ): R {
        val category = enumClass.java.enumConstants?.firstOrNull()
            ?: throw IllegalArgumentException("Invalid retry category: \\${annotation.category}")
        return retryMechanism.execute<I, R, E>(
            category = category,
            maxAttempts = annotation.attempts,
            input = input,
            function = function
        )
    }
} 