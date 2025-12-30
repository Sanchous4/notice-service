package com.example.noticeservice.api.shared.exception

import org.springframework.http.HttpStatus

class InvalidApiVersionException(
    version: String,
    path: String,
) : ApiBaseException(
        "Unsupported API version: $version, path: $path",
        HttpStatus.NOT_FOUND,
        path,
    )
