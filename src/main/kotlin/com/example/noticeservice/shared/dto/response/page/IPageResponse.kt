package com.example.noticeservice.shared.dto.response.page

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

/**
 * Generic interface describing the structure of a paginated response.
 * All implementing classes should preserve the same semantics and annotations.
 */
@Schema(description = "Generic interface defining pagination metadata and items container.")
interface IPageResponse<T> {

    @get:NotNull
    @get:Schema(description = "List of items contained in the current page.", required = true)
    val items: List<T>

    @get:Min(0)
    @get:Schema(description = "Current page index (0-based).", example = "0", required = true)
    val page: Int

    @get:Min(1)
    @get:Schema(description = "Number of items per page.", example = "20", required = true)
    val size: Int

    @get:Min(0)
    @get:Schema(description = "Total number of items across all pages.", example = "120", required = true)
    val totalItems: Long

    @get:Min(0)
    @get:Schema(description = "Total number of pages available.", example = "6", required = true)
    val totalPages: Int

    @get:NotNull
    @get:Schema(description = "Whether this page is the last one.", example = "false", required = true)
    val isLast: Boolean

    @get:NotNull
    @get:Schema(
        description = "True if another page follows this one; false if this is the final page.",
        example = "true",
        required = true
    )
    val hasNext: Boolean

    @get:NotNull
    @get:Schema(
        description = "True if a page exists before the current one; false if this is the first page.",
        example = "false",
        required = true
    )
    val hasPrevious: Boolean
}

