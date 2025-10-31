package com.example.noticeservice.api.entities.notice.service

import com.example.noticeservice.api.entities.notice.dto.request.NoticePatchRequest
import com.example.noticeservice.api.entities.notice.dto.response.NoticeResponse
import com.example.noticeservice.api.entities.notice.mapper.NoticeMapper
import com.example.noticeservice.api.entities.notice.repository.NoticeDBClientRepository
import com.example.noticeservice.api.shared.exception.ServiceIdNullApiException
import com.example.noticeservice.api.shared.validator.PayloadValidator
import jakarta.validation.Validator
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class NoticeWriteService(
    validator: Validator,
    private val noticeDBClientRepository: NoticeDBClientRepository,
    private val noticeMapper: NoticeMapper
) {

    private val payloadValidator = PayloadValidator(validator, NoticeWriteService::class.simpleName)

    fun update(request: NoticePatchRequest): Mono<NoticeResponse> {
        payloadValidator.validateRequest(request)

        if (request.id == null) throw ServiceIdNullApiException(NoticeWriteService::class)

        return noticeDBClientRepository.updateNoticeById(request.id, request.presentFields)
            .map { noticeMapper.convertToResponse(it) }
            .doOnNext { payloadValidator.validateResponse(it) }
    }
}
