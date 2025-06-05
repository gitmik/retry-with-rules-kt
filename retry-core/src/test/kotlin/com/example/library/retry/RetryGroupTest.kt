package com.example.library.retry

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class RetryGroupTest {

    enum class TestCategory { TEST }

    @Test
    fun `test RetryGroupImpl requires at least one rule`() {
        assertThrows(IllegalArgumentException::class.java) {
            RetryGroupImpl(TestCategory.TEST, emptyList())
        }
    }

    @Test
    fun `test RetryGroupBuilder adds rules and builds group`() {
        val builder = RetryGroupBuilder<TestCategory, String, String, Exception>(TestCategory.TEST)
        val rule = object : RetryRule<String, String, Exception> {
            override fun shouldApply(context: RetryContext<String, String, Exception>): Boolean = true
            override fun apply(context: RetryContext<String, String, Exception>): String? = null
        }
        val group = builder.addRule(rule).build()
        assertEquals(TestCategory.TEST, group.category)
        assertEquals(1, group.rules.size)
    }
} 