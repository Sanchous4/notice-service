package com.example.noticeservice.api.entities.notice.controller

import com.example.noticeservice.api.shared.exception.InvalidApiVersionException
import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "Supported API versions for the Notice API")
enum class NoticeApiVersion(
    @get:Schema(description = "Literal version string as used in the API path, e.g. 'v1'")
    val value: String
) {
    @Schema(description = "Version 1 of the API contract")
    V1(Constants.V1);

    override fun toString(): String = value

    companion object {
        fun fromValue(value: String): NoticeApiVersion {
            val nullableValue = entries.firstOrNull { it.value.equals(value, ignoreCase = true) }
            return nullableValue ?: throw InvalidApiVersionException(value, NoticePaths.MAIN)
        }
    }

    object Constants {
        const val V1 = "v1"
    }
}
