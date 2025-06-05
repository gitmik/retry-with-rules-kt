package com.example.library.retry.spring.example.domain

data class PaymentRequest(
    val amount: Double,
    val currency: String,
    val retryCount: Int = 0
) 