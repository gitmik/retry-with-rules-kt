package com.example.library.retry.example.controller

import com.example.library.retry.example.service.User
import com.example.library.retry.example.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): User {
        return userService.getUserById(id)
    }

    @PostMapping("/reset")
    fun resetAttemptCount() {
        userService.resetAttemptCount()
    }
} 