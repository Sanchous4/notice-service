package com.example.noticeservice.api.shared.annotation

import com.example.noticeservice.api.shared.response.ApiErrorResponse
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ApiResponse(
    responseCode = "500",
    description = "Internal server error",
    content = [
        Content(
            mediaType = "application/json",
            schema = Schema(implementation = ApiErrorResponse::class),
        ),
    ],
)
annotation class GeneralSwaggerDoc
