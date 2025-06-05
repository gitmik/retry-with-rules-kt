package com.example.library.retry.micronaut.example.controller

import com.example.library.retry.micronaut.example.domain.PaymentRequest
import com.example.library.retry.micronaut.example.service.PaymentService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/payments")
class PaymentController(private val paymentService: PaymentService) {
    @Post
    fun processPayment(@Body request: PaymentRequest): String {
        return paymentService.processPayment(request)
    }
} 