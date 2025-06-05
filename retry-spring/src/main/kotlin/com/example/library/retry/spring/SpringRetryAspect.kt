package com.example.library.retry.spring

import com.example.library.retry.RetryAspect
import com.example.library.retry.RetryWithRules
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

/**
 * Spring Boot aspect for handling retry functionality.
 * @param T The enum type that identifies retry groups
 */
@Aspect
@Component
class SpringRetryAspect<T : Enum<T>>(
    private val retryAspect: RetryAspect<T>
) {
    @Around("@annotation(retryWithRules)")
    fun around(joinPoint: ProceedingJoinPoint, retryWithRules: RetryWithRules): Any {
        val input = joinPoint.args.firstOrNull()
            ?: throw IllegalArgumentException("Method must have at least one parameter")

        return retryAspect.intercept(
            annotation = retryWithRules,
            input = input,
            function = { joinPoint.proceed() }
        )
    }
} 