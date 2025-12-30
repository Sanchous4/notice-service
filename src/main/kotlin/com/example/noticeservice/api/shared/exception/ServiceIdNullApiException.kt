package com.example.noticeservice.api.shared.exception

import kotlin.reflect.KClass

class ServiceIdNullApiException(
    service: KClass<*>,
) : ServiceRequestApiException(
        service,
        "Id shouldn't be null to update entity",
    )
