package com.example.library.retry.spring.example.controller

import com.example.library.retry.spring.example.domain.PaymentRequest
import com.example.library.retry.spring.example.service.PaymentService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payments")
class PaymentController(private val paymentService: PaymentService) {
    @PostMapping
    fun processPayment(@RequestBody request: PaymentRequest): String {
        return paymentService.processPayment(request)
    }
} 