package com.example.noticeservice.api.entities.notice.controller

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class NoticeApiVersionConverter : Converter<String, NoticeApiVersion> {
    override fun convert(source: String): NoticeApiVersion = NoticeApiVersion.fromValue(source)
}
