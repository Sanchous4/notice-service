package com.example.noticeservice.api.entities.notice.repository

import com.example.noticeservice.api.entities.notice.entity.NoticeEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface NoticeRepository : ReactiveCrudRepository<NoticeEntity, Long> {
    fun findAllBy(pageable: Pageable): Flux<NoticeEntity>
}
