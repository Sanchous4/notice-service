package com.example.noticeservice.api.entities.notice.repository

import com.example.noticeservice.api.entities.notice.entity.NoticeEntity
import com.example.noticeservice.api.shared.repository.GenericPatchRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

val columnTypes = mapOf(
    "id" to java.lang.Long::class.java,
    "title" to String::class.java,
    "content" to String::class.java,
    "created_at" to java.time.LocalDateTime::class.java
)

val allowedFields = setOf("title", "content")

const val tableName = "notice"

@Repository
class NoticeDBClientRepository(
    databaseClient: DatabaseClient,
    objectMapper: ObjectMapper // inject from Spring’s context
) {

    private val genericPatchRepository = GenericPatchRepository<NoticeEntity>(
        databaseClient,
        objectMapper,
        tableName,
        columnTypes
    )

    fun updateNoticeById(id: Long, notice: Map<String, Any?>): Mono<NoticeEntity> {
        return genericPatchRepository.patchById(id, notice, NoticeEntity::class.java, allowedFields)
    }
}

