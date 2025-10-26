package com.example.noticeservice.api.entities.notice.controller

import com.example.noticeservice.api.entities.notice.dto.response.NoticeResponse
import com.example.noticeservice.api.shared.annotations.GeneralSwaggerDoc
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import reactor.core.publisher.Flux

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
    fun getAllNotices(
        @Parameter(
            `in` = ParameterIn.PATH,
            description = "API version — determines which contract or schema is used.",
            required = true,
            example = NoticeApiVersion.Constants.V1,
            schema = Schema(implementation = NoticeApiVersion::class)
        )
        @PathVariable version: NoticeApiVersion
    ): Flux<NoticeResponse>
}
