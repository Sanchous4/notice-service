package com.example.noticeservice.api.shared.validator

import com.example.noticeservice.api.shared.exception.ValidationApiRequestException
import com.example.noticeservice.api.shared.exception.ValidationApiResponseException
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validator

class PayloadValidator(
    private val validator: Validator,
    private val serviceName: String?,
) {
    fun <T> validateSilently(response: T): Set<ConstraintViolation<T>> = validator.validate<T>(response)

    fun <T> validateResponse(response: T) {
        val violations = validateSilently(response)

        if (violations.isNotEmpty()) {
            throw ValidationApiResponseException(serviceName, violations)
        }
    }

    fun <T> validateRequest(request: T) {
        val violations = validateSilently(request)

        if (violations.isNotEmpty()) {
            throw ValidationApiRequestException(serviceName, violations)
        }
    }
}
