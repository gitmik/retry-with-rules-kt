package com.example.library.retry.example

import com.example.library.retry.*

/**
 * Example enum representing different retry scenarios in a business domain.
 */
enum class BusinessScenario {
    PAYMENT_PROCESSING,
    ORDER_FULFILLMENT,
    INVENTORY_CHECK
}

/**
 * Example input data class.
 */
data class PaymentRequest(
    val amount: Double,
    val currency: String,
    val retryCount: Int = 0
)

/**
 * Example retry rule that modifies the payment amount on retry.
 */
class PaymentAmountRule : RetryRule<PaymentRequest, String, Exception> {
    override fun shouldApply(context: RetryContext<PaymentRequest, String, Exception>): Boolean {
        return context.exception != null && context.attempt < context.maxAttempts
    }

    override fun apply(context: RetryContext<PaymentRequest, String, Exception>): PaymentRequest {
        return context.input.copy(
            amount = context.input.amount * 0.95, // Reduce amount by 5% on retry
            retryCount = context.input.retryCount + 1
        )
    }
}

/**
 * Example service using the retry mechanism.
 */
class PaymentService {
    private val retryMechanism = RetryMechanism<BusinessScenario>()
    private val retryAspect = RetryAspect(retryMechanism)

    init {
        // Register retry group for payment processing
        val paymentGroup = RetryGroupBuilder<BusinessScenario, PaymentRequest, String, Exception>(BusinessScenario.PAYMENT_PROCESSING)
            .addRule(PaymentAmountRule())
            .build()
        
        retryMechanism.registerGroup(paymentGroup)
    }

    /**
     * Example method using the RetryWithRules annotation.
     */
    @RetryWithRules(attempts = 3, category = BusinessScenario::class)
    fun processPayment(request: PaymentRequest): String {
        // Simulate payment processing
        if (request.amount < 100) {
            throw Exception("Payment amount too low")
        }
        return "Payment processed successfully: ${request.amount} ${request.currency}"
    }

    /**
     * Example of manual retry execution.
     */
    fun processPaymentManually(request: PaymentRequest): String {
        return retryMechanism.execute(
            category = BusinessScenario.PAYMENT_PROCESSING,
            maxAttempts = 3,
            input = request
        ) { input ->
            processPayment(input)
        }
    }
} 