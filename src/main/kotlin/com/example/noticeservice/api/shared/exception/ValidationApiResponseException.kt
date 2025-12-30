package com.example.noticeservice.api.shared.exception

import jakarta.validation.ConstraintViolation
import org.springframework.http.HttpStatus

class ValidationApiResponseException(
    serviceName: String?,
    violations: Set<ConstraintViolation<*>>,
) : JakartaValidationApiException(serviceName, HttpStatus.INTERNAL_SERVER_ERROR, violations)
