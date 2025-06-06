package com.example.library.retry.example.config

import com.example.library.retry.RetryAspect
import com.example.library.retry.RetryGroup
import com.example.library.retry.RetryGroupBuilder
import com.example.library.retry.RetryMechanism
import com.example.library.retry.RetryRule
import com.example.library.retry.example.service.User
import kotlin.reflect.KClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RetryConfig {
    @Bean
    fun retryMechanism(): RetryMechanism<UserRetryCategory> {
        return RetryMechanism<UserRetryCategory>().apply {
            registerGroup(createUserRetryGroup())
        }
    }

    @Bean
    fun retryAspect(retryMechanism: RetryMechanism<UserRetryCategory>): RetryAspect<UserRetryCategory> {
        return RetryAspect(retryMechanism, UserRetryCategory::class)
    }

    private fun createUserRetryGroup(): RetryGroup<UserRetryCategory> {
        return RetryGroupBuilder<UserRetryCategory, String, User, Exception>(UserRetryCategory.USER_OPERATION)
            .addRule(object : RetryRule<String, User, Exception> {
                override fun shouldApply(context: com.example.library.retry.RetryContext<String, User, Exception>): Boolean {
                    return context.exception != null
                }

                override fun apply(context: com.example.library.retry.RetryContext<String, User, Exception>): String? {
                    return context.input
                }
            })
            .build()
    }
} 