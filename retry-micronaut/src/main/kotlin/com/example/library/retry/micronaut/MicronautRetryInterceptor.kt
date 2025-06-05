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
        val annotationValue = context.getAnnotation(RetryWithRules::class.java)
            ?: return context.proceed()

        val attempts = annotationValue.intValue("attempts").orElse(3)
        val category = annotationValue.classValue("category").orElse(null)
            ?: throw IllegalArgumentException("RetryWithRules annotation must specify a category")

        val annotation = RetryWithRules(attempts = attempts, category = (category as Class<out Enum<*>>).kotlin)

        val input = context.parameterValues.firstOrNull()
            ?: throw IllegalArgumentException("Method must have at least one parameter")

        return retryAspect.intercept<Any, Any, Exception>(
            annotation = annotation,
            input = input,
            function = { context.proceed() }
        )
    }
} 