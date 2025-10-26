package com.example.noticeservice.api.entities.notice.mapper

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NoticeMapperConfig {
    @Bean
    fun noticeMapper(): NoticeMapper = NoticeMapperImpl() // generated class
}
