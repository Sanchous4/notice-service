package com.example.noticeservice.api.entities.notice.controller

import com.example.noticeservice.api.entities.notice.controller.NoticePaths.MAIN
import com.example.noticeservice.api.entities.notice.dto.request.NoticePageRequest
import com.example.noticeservice.api.entities.notice.dto.request.NoticePatchRequest
import com.example.noticeservice.api.entities.notice.dto.response.NoticeResponse
import com.example.noticeservice.api.entities.notice.service.NoticeReadService
import com.example.noticeservice.api.entities.notice.service.NoticeWriteService
import com.example.noticeservice.api.shared.mapper.RequestMapper
import com.example.noticeservice.shared.dto.response.page.PageResponse
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

object NoticePaths {
    const val MAIN = "/api/{version}/notice"
    const val GET_ALL = "/all"
}

@RestController
@RequestMapping(MAIN)
@Validated
class NoticeController(
    private val noticeReadService: NoticeReadService,
    private val noticeWriteService: NoticeWriteService,
    private val requestMapper: RequestMapper
) :
    NoticeControllerSwaggerDoc {

    @GetMapping(NoticePaths.GET_ALL)
    override fun getAll(@PathVariable version: NoticeApiVersion): Flux<NoticeResponse> {
        return noticeReadService.getAll()
    }

    @GetMapping
    override fun getByPageRequest(
        @PathVariable version: NoticeApiVersion,
        @Valid @ModelAttribute pageRequest: NoticePageRequest
    ): Mono<PageResponse<NoticeResponse>> {
        return noticeReadService.getByPageRequest(pageRequest)
    }

    @PatchMapping
    fun update(
        @PathVariable version: NoticeApiVersion,
        @RequestBody noticeMapRequest: Map<String, Any?>
    ): Mono<NoticeResponse> {
        val noticeDTORequest = requestMapper.mapToDto<NoticePatchRequest>(noticeMapRequest)
        return noticeWriteService.update(noticeDTORequest)
    }
}
