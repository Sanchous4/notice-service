package com.example.noticeservice.api.shared.exception

import com.example.noticeservice.shared.exception.BaseSingleLineException
import jakarta.validation.ConstraintViolation

class ValidationResponseException(serviceName: String, violations: Set<ConstraintViolation<*>>) :
    BaseSingleLineException(buildMessage(serviceName, violations)) {
    companion object {
        private fun buildMessage(serviceName: String, violations: Set<ConstraintViolation<*>>): String {
            if (violations.isEmpty()) return "Validation failed with unknown error."

            val details = violations.joinToString(separator = "; ") {
                val path = it.propertyPath?.toString() ?: "<unknown>"
                "$path: ${it.message}"
            }

            return "Validation failed in service $serviceName, details: $details"
        }
    }
}
