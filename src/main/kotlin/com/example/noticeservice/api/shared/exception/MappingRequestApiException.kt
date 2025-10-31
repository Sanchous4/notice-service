package com.example.noticeservice.api.shared.exception

import org.springframework.http.HttpStatus

class MappingRequestApiException(key: Any?, value: Any?) :
    ApiBaseException(
        "Invalid request field: key '$key' with value '$value' cannot be mapped. " +
                "All field names (keys) must be of type String.",
        HttpStatus.BAD_REQUEST
    )
