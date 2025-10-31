package com.example.noticeservice.api.entities.notice.dto.request

import com.example.noticeservice.api.shared.dto.request.RequestFieldsMetadata
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(
    name = "NoticePatchRequest",
    description = "Partial update request for an existing notice. " +
            "Only the fields provided will be updated."
)
data class NoticePatchRequest(

    @field:Schema(
        example = "1",
        description = "Unique identifier of the notice to update.",
        required = true
    )
    @field:NotNull(message = "id is required.")
    val id: Long?,

    @field:Schema(
        example = "System Maintenance",
        description = "Updated title of the notice.",
    )
    @field:NotBlank(message = "title must not be blank.")
    val title: String? = null,

    @field:Schema(
        example = "The system will be offline from 2 AM to 4 AM for maintenance.",
        description = "Updated content of the notice."
    )
    val content: String? = null,

    @field:Schema(hidden = true)
    override val presentFields: MutableMap<String, Any?> = mutableMapOf(),
) : RequestFieldsMetadata
