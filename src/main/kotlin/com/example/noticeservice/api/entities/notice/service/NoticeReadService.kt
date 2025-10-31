package com.example.noticeservice.api.entities.notice.service

import com.example.noticeservice.api.entities.notice.dto.request.NoticePageRequest
import com.example.noticeservice.api.entities.notice.dto.response.NoticeResponse
import com.example.noticeservice.api.entities.notice.mapper.NoticeMapper
import com.example.noticeservice.api.entities.notice.repository.NoticeRepository
import com.example.noticeservice.api.shared.validator.PayloadValidator
import com.example.noticeservice.shared.dto.response.page.PageResponse
import com.example.noticeservice.shared.mapper.PageMapper
import jakarta.validation.Validator
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class NoticeReadService(
    private val repository: NoticeRepository,
    private val mapper: NoticeMapper,
    validator: Validator,
) {

    private val payloadValidator = PayloadValidator(validator, NoticeReadService::class.simpleName)

    fun getAll(): Flux<NoticeResponse> {
        return repository.findAll()
            .map { mapper.convertToResponse(it) }
            .doOnNext { payloadValidator.validateResponse(it) }
    }

    fun getByPageRequest(pageRequest: NoticePageRequest): Mono<PageResponse<NoticeResponse>> {
        val pageable = PageRequest.of(pageRequest.page, pageRequest.size)

        val mappedData = repository.findAllBy(pageable)
            .map { mapper.convertToResponse(it) }
            .doOnNext { payloadValidator.validateResponse(it) }
            .collectList()

        val totalEntities = repository.count()

        return Mono.zip(mappedData, totalEntities)
            .map { tuple ->
                NoticePageData(tuple.t1, tuple.t2)
            }
            .map { (content, total) ->
                PageMapper.mapPageData(pageData = PageImpl(content, pageable, total))
            }
            .doOnNext { payloadValidator.validateResponse(it) }
    }

}
