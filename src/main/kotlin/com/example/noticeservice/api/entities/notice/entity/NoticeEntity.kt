package com.example.noticeservice.api.entities.notice.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "notice")
data class NoticeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = false)
    val title: String = "",

    @Column(name = "content", nullable = false)
    val content: String = "",

    @CreationTimestamp
    @Column(
        name = "created_at",
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
        updatable = false
    )
    val createdAt: LocalDateTime? = null
)
