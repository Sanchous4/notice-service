package com.example.noticeservice.api.shared.exception

import org.springframework.http.HttpStatus
import kotlin.reflect.KClass

open class RepositoryApiException(
    repository: KClass<*>,
    message: String,
    httpStatus: HttpStatus,
) : ApiBaseException(
        "Error occurred in repository: ${repository.simpleName}, message: $message",
        httpStatus,
    )
