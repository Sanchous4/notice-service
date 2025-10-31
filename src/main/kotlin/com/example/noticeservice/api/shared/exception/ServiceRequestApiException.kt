package com.example.noticeservice.api.shared.exception

import org.springframework.http.HttpStatus
import kotlin.reflect.KClass

open class ServiceRequestApiException(service: KClass<*>, message: String) :
    ServiceApiException(
        service,
        message,
        HttpStatus.BAD_REQUEST,
    )
