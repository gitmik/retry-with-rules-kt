package com.example.library.retry

/**
 * Main class for handling retry operations with rules.
 * @param T The enum type that identifies retry groups
 */
class RetryMechanism<T : Enum<T>> {
    private val groups = mutableMapOf<T, RetryGroup<T>>()

    /**
     * Registers a retry group.
     * @param group The retry group to register
     */
    fun registerGroup(group: RetryGroup<T>) {
        groups[group.category] = group
    }

    /**
     * Executes a function with retry rules.
     * @param I The type of the input arguments
     * @param R The type of the return value
     * @param E The type of exceptions that can be thrown
     * @param category The retry group category to use
     * @param maxAttempts Maximum number of retry attempts
     * @param input The input arguments
     * @param function The function to execute
     * @return The result of the function execution
     * @throws E if all retry attempts fail
     */
    @Suppress("UNCHECKED_CAST")
    fun <I, R, E : Throwable> execute(
        category: T,
        maxAttempts: Int,
        input: I,
        function: (I) -> R
    ): R {
        val group = groups[category] ?: throw IllegalArgumentException("No retry group found for category: $category")
        val rules = group.rules as List<RetryRule<I, R, E>>
        val history = mutableListOf<RetryAttempt<I, R, E>>()
        
        var currentInput = input
        var lastException: E? = null
        var lastResult: R? = null

        for (attempt in 1..maxAttempts) {
            try {
                lastResult = function(currentInput)
                return lastResult
            } catch (e: Throwable) {
                if (e is Exception) {
                    lastException = e as E
                } else {
                    throw e
                }
            }

            val attempt = RetryAttempt(attempt, currentInput, lastResult, lastException)
            history.add(attempt)

            val context = RetryContext(
                attempt = attempt.attempt,
                maxAttempts = maxAttempts,
                input = currentInput,
                result = lastResult,
                exception = lastException,
                history = history.toList()
            )

            // Apply rules in order
            for (rule in rules) {
                if (rule.shouldApply(context)) {
                    val modifiedInput = rule.apply(context)
                    if (modifiedInput != null) {
                        currentInput = modifiedInput
                        break
                    }
                }
            }
        }

        throw lastException ?: IllegalStateException("Retry failed but no exception was captured")
    }
} 