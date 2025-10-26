package com.example.noticeservice.api.shared.response

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import java.time.OffsetDateTime

@Schema(description = "Standard structure for API error responses")
data class ApiErrorResponse(

    @field:Schema(description = "HTTP status code", example = "400")
    val httpStatus: Int,

    @field:Schema(description = "Short description of the error", example = "Bad Request")
    val error: String,

    @field:Schema(
        description = "Detailed error message for debugging or client feedback",
        example = "Unsupported API version: v9"
    )
    val message: String? = null,

    @field:Schema(description = "Timestamp when the error occurred", example = "2025-10-26T15:32:09Z")
    val timestamp: OffsetDateTime = OffsetDateTime.now(),

    @field:Schema(description = "Path of the request that caused the error", example = "/api/v9/notice")
    val path: String? = null
) {
    companion object {
        fun of(status: HttpStatus, message: String?, path: String? = null): ApiErrorResponse =
            ApiErrorResponse(
                httpStatus = status.value(),
                error = status.reasonPhrase,
                message = message,
                path = path
            )
    }
}
