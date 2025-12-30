package com.example.noticeservice.api.shared.exception

import org.springframework.http.HttpStatus
import kotlin.reflect.KClass

open class RepositoryExtraFieldsApiException(
    repository: KClass<*>,
    extraFields: Map<String, Any?>,
    allowedFields: Set<String>,
) : RepositoryApiException(
        repository,
        buildMessage(extraFields, allowedFields),
        HttpStatus.BAD_REQUEST,
    ) {
    companion object {
        fun buildMessage(
            fields: Map<String, Any?>,
            allowed: Set<String>,
        ): String {
            if (fields.isEmpty()) {
                return "Request contains no extra fields."
            }

            val extras =
                fields.entries.joinToString(", ") { (key, value) ->
                    "'$key' = ${value?.toString() ?: "null"}"
                }

            val allowedList = allowed.joinToString(", ") { "'$it'" }

            return buildString {
                appendLine("Extra fields detected in request:")
                appendLine("  → $extras")
                append("Allowed fields are: $allowedList.")
            }
        }
    }
}
