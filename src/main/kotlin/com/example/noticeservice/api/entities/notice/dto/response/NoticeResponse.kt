package com.example.noticeservice.api.entities.notice.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Schema(
    name = "NoticeResponse",
    description = "Represents a system notice returned to clients."
)
data class NoticeResponse(

    @field:Schema(
        example = "1",
        description = "Unique identifier of the notice.",
        required = true
    )
    @field:NotNull
    val id: Long?,

    @field:Schema(
        example = "System Maintenance",
        description = "Short title summarizing the notice.",
        required = true
    )
    @field:NotBlank(message = "Title is required.")
    val title: String,

    @field:Schema(
        example = "The system will be offline from 2 AM to 4 AM for scheduled maintenance.",
        description = "Detailed message content of the notice."
    )
    val content: String?,

    @field:Schema(
        example = "2025-10-26T11:53:09.106Z",
        description = "Creation timestamp in ISO-8601 format (UTC).",
        required = true
    )
    @field:NotNull
    val createdAt: LocalDateTime?
)
