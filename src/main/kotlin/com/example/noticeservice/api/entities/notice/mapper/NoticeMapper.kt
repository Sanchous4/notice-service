package com.example.noticeservice.api.entities.notice.mapper

import com.example.noticeservice.api.entities.notice.dto.response.NoticeResponse
import com.example.noticeservice.api.entities.notice.entity.NoticeEntity
import com.syouth.kmapper.processor_annotations.Mapper

@Mapper
interface NoticeMapper {
    fun convertToResponse(entity: NoticeEntity): NoticeResponse
}
