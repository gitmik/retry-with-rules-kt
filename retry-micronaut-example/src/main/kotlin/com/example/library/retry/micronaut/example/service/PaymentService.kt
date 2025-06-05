package com.example.library.retry.micronaut.example.service

import com.example.library.retry.RetryWithRules
import com.example.library.retry.micronaut.example.domain.BusinessScenario
import com.example.library.retry.micronaut.example.domain.PaymentRequest
import jakarta.inject.Singleton
import java.util.concurrent.atomic.AtomicInteger

@Singleton
class PaymentService {
    private val attemptCounter = AtomicInteger(0)

    @RetryWithRules(attempts = 3, category = BusinessScenario::class)
    fun processPayment(request: PaymentRequest): String {
        val attempt = attemptCounter.incrementAndGet()
        
        // Simulate payment processing with failures
        if (attempt < 3 && request.amount < 100) {
            throw PaymentException("Payment amount too low: ${request.amount}")
        }
        
        return "Payment processed successfully: ${request.amount} ${request.currency} (attempt: $attempt)"
    }
}

class PaymentException(message: String) : RuntimeException(message) 