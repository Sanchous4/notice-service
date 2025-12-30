package com.example.noticeservice.api.shared.exception

import com.example.noticeservice.api.shared.constant.ApiConstant
import jakarta.validation.ConstraintViolation
import org.springframework.http.HttpStatus

open class JakartaValidationApiException(
    serviceName: String?,
    httpStatus: HttpStatus,
    violations: Set<ConstraintViolation<*>>,
) : ApiBaseException(
        buildMessage(serviceName, violations),
        httpStatus,
    ) {
    companion object {
        private fun buildMessage(
            serviceName: String? = ApiConstant.UNKNOWN_SERVICE_NAME,
            violations: Set<ConstraintViolation<*>>,
        ): String {
            if (violations.isEmpty()) {
                return "Validation failed in service $serviceName, but no violations were found."
            }

            val details =
                violations.joinToString(separator = "; ") {
                    val path = it.propertyPath?.toString() ?: "<unknown>"
                    "$path: ${it.message}"
                }

            return "Validation failed in service $serviceName, details: $details"
        }
    }
}
