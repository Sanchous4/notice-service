package com.example.noticeservice.shared.mapper

import com.example.noticeservice.shared.dto.response.page.PageResponse
import org.springframework.data.domain.Page

object PageMapper {
    fun <T> mapPageData(pageData: Page<T>): PageResponse<T> {
        return PageResponse(
            items = pageData.content,
            page = pageData.number,
            size = pageData.size,
            totalItems = pageData.totalElements,
            totalPages = pageData.totalPages,
            isLast = pageData.isLast,
            hasNext = pageData.hasNext(),
            hasPrevious = pageData.hasPrevious()
        )
    }
}
