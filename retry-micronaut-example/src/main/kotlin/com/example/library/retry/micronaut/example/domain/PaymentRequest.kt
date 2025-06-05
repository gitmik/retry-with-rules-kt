package com.example.library.retry.micronaut.example.domain

data class PaymentRequest(
    val amount: Double,
    val currency: String,
    val retryCount: Int = 0
) 