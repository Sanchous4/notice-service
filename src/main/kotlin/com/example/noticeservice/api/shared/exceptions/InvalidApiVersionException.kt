package com.example.noticeservice.api.shared.exceptions

import org.springframework.http.HttpStatus

class InvalidApiVersionException(version: String, path: String) :
    ApiBaseException(
        "Unsupported API version: $version, path: $path",
        HttpStatus.UPGRADE_REQUIRED,
        path
    )
