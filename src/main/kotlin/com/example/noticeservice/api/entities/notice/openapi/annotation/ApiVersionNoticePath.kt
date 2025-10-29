package com.example.noticeservice.api.entities.notice.openapi.annotation

import com.example.noticeservice.api.entities.notice.controller.NoticeApiVersion
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Parameter(
    `in` = ParameterIn.PATH,
    description = "API version — determines which contract or schema is used.",
    required = true,
    example = NoticeApiVersion.Constants.V1,
    schema = Schema(implementation = NoticeApiVersion::class)
)
annotation class ApiVersionNoticePath()
