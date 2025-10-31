package com.example.noticeservice.api.shared.exception

import jakarta.validation.ConstraintViolation
import org.springframework.http.HttpStatus

class ValidationApiRequestException(serviceName: String?, violations: Set<ConstraintViolation<*>>) :
    JakartaValidationApiException(serviceName, HttpStatus.UNPROCESSABLE_ENTITY, violations)

