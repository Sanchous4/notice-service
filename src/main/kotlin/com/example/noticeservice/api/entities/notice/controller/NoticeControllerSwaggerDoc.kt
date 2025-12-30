package com.example.noticeservice.api.entities.notice.controller

import com.example.noticeservice.api.entities.notice.dto.request.NoticePageRequest
import com.example.noticeservice.api.entities.notice.dto.request.NoticePatchRequest
import com.example.noticeservice.api.entities.notice.dto.response.NoticeResponse
import com.example.noticeservice.api.entities.notice.openapi.annotation.ApiVersionNoticePath
import com.example.noticeservice.api.entities.notice.openapi.response.PageNoticeResponse
import com.example.noticeservice.api.shared.annotation.GeneralSwaggerDoc
import com.example.noticeservice.shared.dto.response.page.PageResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Tag(
    name = "Notice API",
    description = "Endpoints for managing and retrieving system notices",
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
                        array = ArraySchema(schema = Schema(implementation = NoticeResponse::class)),
                    ),
                ],
            ),
        ],
    )
    @GeneralSwaggerDoc
    fun getAll(
        @ApiVersionNoticePath
        version: NoticeApiVersion,
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
                        schema = Schema(implementation = PageNoticeResponse::class),
                    ),
                ],
            ),
        ],
    )
    @GeneralSwaggerDoc
    fun getByPageRequest(
        @ApiVersionNoticePath
        version: NoticeApiVersion,
        @Valid pageRequest: NoticePageRequest,
    ): Mono<PageResponse<NoticeResponse>>

    @Operation(
        summary = "Patch notice",
        description = "Partially update an existing notice by providing the fields to change.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully updated notice",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = NoticeResponse::class))],
            ),
        ],
    )
    @GeneralSwaggerDoc
    fun update(
        @ApiVersionNoticePath
        version: NoticeApiVersion,
        @RequestBody(
            description = "Fields to update on the notice. Only provided fields will be changed.",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = NoticePatchRequest::class),
                    examples = [
                        ExampleObject(
                            name = "Update title and content",
                            summary = "Update the title and content of the notice with id 1",
                            value =
                                """{
                                  "id": 1,
                                  "title": "System Maintenance",
                                  "content": "The system will be offline from 02:00 to 04:00."
                                }"""
                        ),
                    ],
                ),
            ],
        )
        noticeMapRequest: Map<String, Any?>,
    ): Mono<NoticeResponse>
}
