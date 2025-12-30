package com.example.noticeservice.api.shared.mapper

import com.example.noticeservice.api.shared.dto.request.RequestFieldsMetadata
import com.example.noticeservice.api.shared.helper.RequestCleaner
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class RequestMapper(
    private val objectMapper: ObjectMapper,
) {
    fun <T> mapToDto(
        cleaned: Map<String, Any?>,
        targetClass: Class<T>,
    ): T
            where T : RequestFieldsMetadata {
        val dto = objectMapper.convertValue(cleaned, targetClass)
        dto.presentFields.putAll(cleaned)
        return dto
    }

    final inline fun <reified T> mapToDto(request: Map<String, Any?>): T
            where T : RequestFieldsMetadata {
        val cleaned = RequestCleaner.cleanseMap(request)
        return mapToDto(cleaned, T::class.java)
    }
}
