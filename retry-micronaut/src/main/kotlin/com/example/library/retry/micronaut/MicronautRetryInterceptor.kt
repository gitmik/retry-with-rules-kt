package com.example.library.retry.micronaut

import com.example.library.retry.RetryAspect
import com.example.library.retry.RetryWithRules
import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import io.micronaut.context.annotation.Prototype
import jakarta.inject.Inject
import jakarta.inject.Singleton

/**
 * Micronaut interceptor for handling retry functionality.
 * @param T The enum type that identifies retry groups
 */
@Singleton
@InterceptorBean(RetryWithRules::class)
class MicronautRetryInterceptor<T : Enum<T>> @Inject constructor(
    private val retryAspect: RetryAspect<T>
) : MethodInterceptor<Any, Any> {

    override fun intercept(context: MethodInvocationContext<Any, Any>): Any {
        val annotation = context.getAnnotation(RetryWithRules::class.java)
            ?: return context.proceed()

        val input = context.parameterValues.firstOrNull()
            ?: throw IllegalArgumentException("Method must have at least one parameter")

        return retryAspect.intercept(
            annotation = annotation,
            input = input,
            function = { context.proceed() }
        )
    }
} 