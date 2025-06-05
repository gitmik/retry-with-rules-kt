package com.example.library.retry

/**
 * Interface for retry rule groups that can be identified by an enum.
 * @param T The enum type that identifies this retry group
 */
interface RetryGroup<T : Enum<T>> {
    val category: T
    val rules: List<RetryRule<*, *, *>>
}

/**
 * Implementation of RetryGroup that maintains ordered rules.
 * @param T The enum type that identifies this retry group
 * @param I The type of the input arguments
 * @param R The type of the return value
 * @param E The type of exceptions that can be thrown
 */
class RetryGroupImpl<T : Enum<T>, I, R, E : Throwable>(
    override val category: T,
    override val rules: List<RetryRule<I, R, E>>
) : RetryGroup<T> {
    init {
        require(rules.isNotEmpty()) { "Retry group must contain at least one rule" }
    }
}

/**
 * Builder for creating retry groups with ordered rules.
 * @param T The enum type that identifies this retry group
 * @param I The type of the input arguments
 * @param R The type of the return value
 * @param E The type of exceptions that can be thrown
 */
class RetryGroupBuilder<T : Enum<T>, I, R, E : Throwable>(
    private val category: T
) {
    private val rules = mutableListOf<RetryRule<I, R, E>>()

    fun addRule(rule: RetryRule<I, R, E>): RetryGroupBuilder<T, I, R, E> {
        rules.add(rule)
        return this
    }

    fun build(): RetryGroupImpl<T, I, R, E> {
        return RetryGroupImpl(category, rules.toList())
    }
} 