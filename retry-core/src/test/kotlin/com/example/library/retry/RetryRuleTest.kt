package com.example.library.retry

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class RetryRuleTest {

    @Test
    fun `test RetryContext holds correct attempt information`() {
        val context = RetryContext(1, 3, "input", "result", RuntimeException("Test exception"), emptyList())
        assertEquals(1, context.attempt)
        assertEquals(3, context.maxAttempts)
        assertEquals("input", context.input)
        assertEquals("result", context.result)
        assertNotNull(context.exception)
        assertTrue(context.history.isEmpty())
    }

    @Test
    fun `test RetryAttempt holds correct attempt information`() {
        val attempt = RetryAttempt(1, "input", "result", RuntimeException("Test exception"))
        assertEquals(1, attempt.attempt)
        assertEquals("input", attempt.input)
        assertEquals("result", attempt.result)
        assertNotNull(attempt.exception)
    }
} 