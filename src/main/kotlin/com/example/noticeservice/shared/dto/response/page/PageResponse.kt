package com.example.noticeservice.shared.dto.response.page

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Generic wrapper for paginated API responses.")
data class PageResponse<T>(
    override val items: List<T>,
    override val page: Int,
    override val size: Int,
    override val totalItems: Long,
    override val totalPages: Int,
    override val isLast: Boolean,
    override val hasNext: Boolean,
    override val hasPrevious: Boolean,
) : IPageResponse<T>
