package com.example.library.retry.example

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ExampleUsageTest {
    @Test
    fun `test payment processing with retry`() {
        val service = PaymentService()
        val request = PaymentRequest(amount = 90.0, currency = "USD")

        // Should fail on first attempt (amount < 100)
        // Then retry with reduced amount (90 * 0.95 = 85.5)
        // Then retry with further reduced amount (85.5 * 0.95 = 81.225)
        // Then fail with exception
        assertFailsWith<Exception> {
            service.processPaymentManually(request)
        }

        // Test with sufficient amount
        val successfulRequest = PaymentRequest(amount = 150.0, currency = "USD")
        val result = service.processPaymentManually(successfulRequest)
        assertEquals("Payment processed successfully: 150.0 USD", result)
    }

    @Test
    fun `test payment processing with annotation`() {
        val service = PaymentService()
        val request = PaymentRequest(amount = 90.0, currency = "USD")

        // Should fail on first attempt (amount < 100)
        // Then retry with reduced amount (90 * 0.95 = 85.5)
        // Then retry with further reduced amount (85.5 * 0.95 = 81.225)
        // Then fail with exception
        assertFailsWith<Exception> {
            service.processPayment(request)
        }

        // Test with sufficient amount
        val successfulRequest = PaymentRequest(amount = 150.0, currency = "USD")
        val result = service.processPayment(successfulRequest)
        assertEquals("Payment processed successfully: 150.0 USD", result)
    }
} 