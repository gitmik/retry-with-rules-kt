package com.example.library.retry.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RetryExampleApplication

fun main(args: Array<String>) {
    runApplication<RetryExampleApplication>(*args)
} 