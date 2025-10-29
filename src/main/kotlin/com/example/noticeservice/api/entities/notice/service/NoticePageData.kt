package com.example.noticeservice.api.entities.notice.service

import com.example.noticeservice.api.entities.notice.dto.response.NoticeResponse

data class NoticePageData(
    val content: List<NoticeResponse>,
    val total: Long
)

