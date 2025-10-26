package com.example.noticeservice.api.entities.notice.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class NoticeResponse(
    @field:Schema(example = "1", description = "Unique identifier of the notice")
    val id: Long,

    @field:Schema(example = "System Maintenance", description = "Title of the notice")
    val title: String,

    @field:Schema(example = "The system will be offline from 2 AM to 4 AM for maintenance.")
    val content: String,

    @field:Schema(example = "2025-10-26T11:53:09.106Z")
    val createdAt: LocalDateTime
)
