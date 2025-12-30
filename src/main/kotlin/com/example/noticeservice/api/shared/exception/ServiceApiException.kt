package com.example.noticeservice.api.shared.exception

import org.springframework.http.HttpStatus
import kotlin.reflect.KClass

open class ServiceApiException(
    service: KClass<*>,
    message: String,
    httpStatus: HttpStatus,
) : ApiBaseException(
        "Service $service error: $message",
        httpStatus,
    )
