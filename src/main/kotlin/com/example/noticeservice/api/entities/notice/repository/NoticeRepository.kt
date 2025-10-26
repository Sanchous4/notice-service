package com.example.noticeservice.api.entities.notice.repository

import com.example.noticeservice.api.entities.notice.entity.NoticeEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NoticeRepository : ReactiveCrudRepository<NoticeEntity, Long> {

}

