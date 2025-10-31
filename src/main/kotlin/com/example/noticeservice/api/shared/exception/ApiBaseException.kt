package com.example.noticeservice.api.shared.exception

import com.example.noticeservice.api.shared.response.ApiErrorResponse
import com.example.noticeservice.shared.exception.BaseSingleLineException
import org.springframework.http.HttpStatus

/**
 * A reusable base class for lightweight, single-line exceptions.
 * - Logs automatically upon creation.
 * - Suppresses expensive stack-trace generation.
 * - Prints concise one-line messages.
 *
 * @param message The error message to log and include in the exception.
 * @param cause Optional cause of the exception.
 */
open class ApiBaseException(
    message: String,
    val httpStatus: HttpStatus,
    val path: String? = null
) : BaseSingleLineException(message) {

    fun toApiResponse(defaultPath: String? = null): ApiErrorResponse {
        return ApiErrorResponse.of(
            httpStatus,
            message,
            path ?: defaultPath
        )
    }
}
