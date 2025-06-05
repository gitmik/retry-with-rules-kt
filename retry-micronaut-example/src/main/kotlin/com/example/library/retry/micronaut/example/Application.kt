package com.example.library.retry.micronaut.example

import io.micronaut.runtime.Micronaut

fun main(args: Array<String>) {
    Micronaut.build()
        .args(*args)
        .packages("com.example.library.retry.micronaut.example")
        .start()
} 