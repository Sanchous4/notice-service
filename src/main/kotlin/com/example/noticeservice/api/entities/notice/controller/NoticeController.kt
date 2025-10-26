package com.example.noticeservice.api.entities.notice.controller

import com.example.noticeservice.api.entities.notice.entity.NoticeEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

const val NOTICE_PATH = "/api/{version}/notice"

@RestController
@RequestMapping(NOTICE_PATH)
class NoticeController : NoticeControllerSwaggerDoc {

    @GetMapping
    override fun getAllNotices(@PathVariable version: NoticeApiVersion): Flux<NoticeEntity> {
        return Flux.empty()
    }
}
