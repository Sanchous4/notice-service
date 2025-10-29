package com.example.noticeservice.api.entities.notice.openapi.response

import com.example.noticeservice.api.entities.notice.dto.response.NoticeResponse
import com.example.noticeservice.shared.dto.response.page.IPageResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Paginated NoticeResponse result.")
interface PageNoticeResponse : IPageResponse<NoticeResponse>
