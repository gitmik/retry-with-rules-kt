package com.example.library

import kotlin.test.Test
import kotlin.test.assertEquals

class ExampleTest {
    @Test
    fun testGreet() {
        val example = Example()
        assertEquals("Hello, World!", example.greet("World"))
    }

    @Test
    fun testAdd() {
        val example = Example()
        assertEquals(5, example.add(2, 3))
    }
} 