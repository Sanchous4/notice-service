package com.example.noticeservice.api.shared.dto.request

import com.fasterxml.jackson.annotation.JsonIgnore

interface RequestFieldsMetadata {
    @get:JsonIgnore
    val presentFields: MutableMap<String, Any?>
}
