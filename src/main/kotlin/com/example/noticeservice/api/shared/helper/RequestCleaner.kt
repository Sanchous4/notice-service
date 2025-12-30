package com.example.noticeservice.api.shared.helper

import com.example.noticeservice.api.shared.exception.MappingRequestApiException

object RequestCleaner {
    fun cleanseMap(data: Map<*, *>): Map<String, Any?> =
        data
            .mapNotNull { (key, value) ->
                if (key !is String) throw MappingRequestApiException(key, value)

                val cleanedValue =
                    when (value) {
                        is String -> value.ifBlank { null }
                        is Map<*, *> -> cleanseMap(value)
                        is Collection<*> -> cleanseList(value).ifEmpty { null }
                        else -> value
                    }

                key to cleanedValue
            }.toMap()

    private fun cleanseList(data: Collection<*>): Collection<Any?> =
        data.mapNotNull { element ->
            when (element) {
                is Map<*, *> -> cleanseMap(element)
                is Collection<*> -> cleanseList(element)
                else -> element
            }
        }
}
