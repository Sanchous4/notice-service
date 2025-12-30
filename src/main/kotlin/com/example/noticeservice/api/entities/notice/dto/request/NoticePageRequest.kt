package com.example.noticeservice.api.entities.notice.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class NoticePageRequest(
    @field:Min(0, message = "Page number cannot be negative")
    @field:Schema(description = "Zero-based page index", example = "0", minimum = "0")
    val page: Int = 0,
    @field:Min(1, message = "Page size must be at least 1")
    @field:Max(100, message = "Page size cannot exceed 100")
    @field:Schema(description = "Number of notices per page", example = "10", minimum = "1", maximum = "100")
    val size: Int = 10,
)
