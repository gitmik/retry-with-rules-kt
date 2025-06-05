package com.example.library.retry.spring.example.service

import com.example.library.retry.RetryWithRules
import com.example.library.retry.spring.example.domain.BusinessScenario
import com.example.library.retry.spring.example.domain.PaymentRequest
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
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