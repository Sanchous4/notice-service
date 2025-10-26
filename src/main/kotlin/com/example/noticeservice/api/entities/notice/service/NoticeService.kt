package com.example.noticeservice.api.entities.notice.service

import com.example.noticeservice.api.entities.notice.dto.response.NoticeResponse
import com.example.noticeservice.api.entities.notice.mapper.NoticeMapper
import com.example.noticeservice.api.entities.notice.repository.NoticeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class NoticeService(
    private val repository: NoticeRepository,
    private val mapper: NoticeMapper
) {
    fun getAll(): Flux<NoticeResponse> {
        return repository.findAll()
            .map { mapper.convertToResponse(it) }
    }
}
