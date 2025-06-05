package com.example.library.retry

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class RetryMechanismTest {

    enum class TestCategory { TEST }

    @Test
    fun `test registerGroup adds group to mechanism`() {
        val mechanism = RetryMechanism<TestCategory>()
        val group = RetryGroupImpl(TestCategory.TEST, emptyList())
        mechanism.registerGroup(group)
        assertNotNull(mechanism.execute(TestCategory.TEST, 1, "input") { it })
    }

    @Test
    fun `test execute throws IllegalArgumentException for unknown category`() {
        val mechanism = RetryMechanism<TestCategory>()
        assertThrows(IllegalArgumentException::class.java) {
            mechanism.execute(TestCategory.TEST, 1, "input") { it }
        }
    }

    @Test
    fun `test execute retries on exception`() {
        val mechanism = RetryMechanism<TestCategory>()
        val group = RetryGroupImpl(TestCategory.TEST, emptyList())
        mechanism.registerGroup(group)
        var attempts = 0
        assertThrows(RuntimeException::class.java) {
            mechanism.execute(TestCategory.TEST, 3, "input") {
                attempts++
                throw RuntimeException("Test exception")
            }
        }
        assertEquals(3, attempts)
    }
} 