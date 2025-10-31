package com.example.noticeservice.api.entities.notice.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("notice")
data class NoticeEntity(

    @Id
    val id: Long? = null,

    @Column("title")
    val title: String,

    @Column("content")
    val content: String?,

    @CreatedDate
    @Column("created_at")
    val createdAt: LocalDateTime? = null
)
