package com.example.noticeservice.api.shared.validator

import com.example.noticeservice.api.shared.exception.ValidationResponseException
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validator

class ResponseValidator(private val validator: Validator, private val serviceName: String) {
    fun <T> validateSilently(response: T): Set<ConstraintViolation<T>> {
        return validator.validate<T>(response)
    }

    fun <T> validate(response: T) {
        val violations = validateSilently(response)

        if (violations.isNotEmpty()) {
            throw ValidationResponseException(serviceName, violations)
        }
    }
}
