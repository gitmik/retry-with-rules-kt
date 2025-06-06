package com.example.library.retry.example.service

import com.example.library.retry.RetryWithRules
import com.example.library.retry.example.config.UserRetryCategory
import org.springframework.stereotype.Service

@Service
class UserService {
    private var attemptCount = 0

    @RetryWithRules(
        attempts = 3,
        category = UserRetryCategory::class
    )
    fun getUserById(id: String): User {
        attemptCount++
        if (attemptCount < 3) {
            throw RuntimeException("Failed to get user, attempt $attemptCount")
        }
        return User(id, "John Doe")
    }

    fun resetAttemptCount() {
        attemptCount = 0
    }
}

data class User(
    val id: String,
    val name: String
) 