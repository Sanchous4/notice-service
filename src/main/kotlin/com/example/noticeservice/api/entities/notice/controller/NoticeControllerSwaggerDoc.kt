package com.example.noticeservice.api.entities.notice.controller

import com.example.noticeservice.api.entities.notice.dto.request.NoticePageRequest
import com.example.noticeservice.api.entities.notice.dto.response.NoticeResponse
import com.example.noticeservice.api.entities.notice.openapi.annotation.ApiVersionNoticePath
import com.example.noticeservice.api.entities.notice.openapi.response.PageNoticeResponse
import com.example.noticeservice.api.shared.annotation.GeneralSwaggerDoc
import com.example.noticeservice.shared.dto.response.page.PageResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Tag(
    name = "Notice API",
    description = "Endpoints for managing and retrieving system notices"
)
interface NoticeControllerSwaggerDoc {

    @Operation(
        summary = "Retrieve all notices",
        description = "Returns a list of all available notices in the system.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved list of notices",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = NoticeResponse::class))
                    )
                ]
            ),
        ]
    )
    @GeneralSwaggerDoc
    fun getAll(
        @ApiVersionNoticePath
        version: NoticeApiVersion
    ): Flux<NoticeResponse>

    @Operation(
        summary = "Retrieve notices by page params",
        description = "Returns a list of notices according to this page params (size and page).",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved list of notices",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PageNoticeResponse::class)
                    )
                ]
            ),
        ]
    )
    @GeneralSwaggerDoc
    fun getByPageRequest(
        @ApiVersionNoticePath
        version: NoticeApiVersion,
        @Valid pageRequest: NoticePageRequest
    ): Mono<PageResponse<NoticeResponse>>
}
