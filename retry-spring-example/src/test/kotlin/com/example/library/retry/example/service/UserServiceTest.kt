package com.example.library.retry.example.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class UserServiceTest {

    @Test
    fun `test getUserById retries on failure and succeeds on third attempt`() {
        val userService = UserService()
        
        // First attempt should fail
        assertThrows(RuntimeException::class.java) {
            userService.getUserById("1")
        }
        
        // Second attempt should fail
        assertThrows(RuntimeException::class.java) {
            userService.getUserById("1")
        }
        
        // Third attempt should succeed
        val user = userService.getUserById("1")
        assertEquals("1", user.id)
        assertEquals("John Doe", user.name)
    }

    @Test
    fun `test resetAttemptCount resets the attempt counter`() {
        val userService = UserService()
        
        // First attempt should fail
        assertThrows(RuntimeException::class.java) {
            userService.getUserById("1")
        }
        
        // Reset attempt count
        userService.resetAttemptCount()
        
        // Should fail again after reset
        assertThrows(RuntimeException::class.java) {
            userService.getUserById("1")
        }
    }
} 