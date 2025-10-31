package com.example.noticeservice.api.entities.notice.repository

import com.example.noticeservice.api.entities.notice.entity.NoticeEntity
import com.example.noticeservice.api.shared.exception.RepositoryExtraFieldsApiException
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

@Repository
class NoticeDBClientRepository(
    private val databaseClient: DatabaseClient,
    private val objectMapper: ObjectMapper // inject from Spring’s context
) {

    fun updateNoticeById(id: Long, fields: Map<String, Any?>): Mono<NoticeEntity> {
        if (fields.isEmpty()) return Mono.empty()

        val primitiveFields = fields.filterValues { isNotMapValue(it) }.filterKeys { it != "id" }
        if (primitiveFields.isEmpty()) return Mono.empty()

        val extraFields = primitiveFields
            .filterKeys { it !in allowedFields } // collect only disallowed keys
            .toMap()

        if (!extraFields.isEmpty()) return Mono.error(
            RepositoryExtraFieldsApiException(
                NoticeDBClientRepository::class,
                extraFields,
                allowedFields
            )
        )

        val setClause = primitiveFields.keys.joinToString(", ") { "$it = :$it" }
        val sql = "UPDATE notice SET $setClause WHERE id = :id RETURNING *"

        var spec = databaseClient.sql(sql).bind("id", id)
        primitiveFields.forEach { (key, value) ->
            spec = bindValue(spec, key, value)
        }

        return spec.map { row, meta ->
            val columnMap = mutableMapOf<String, Any?>()
            meta.columnMetadatas.forEach { col ->
                val name = col.name
                val type = columnTypes[name] ?: Any::class.java
                columnMap[name] = row.get(name, type)
            }

            objectMapper.convertValue(columnMap, NoticeEntity::class.java)
        }.one()
    }

    private fun bindValue(
        spec: DatabaseClient.GenericExecuteSpec,
        key: String,
        value: Any?
    ): DatabaseClient.GenericExecuteSpec {
        val columnType = columnTypes.get(key)

        if (columnType == null) return spec

        return when (value) {
            null -> spec.bindNull(key, columnType)
            is Collection<*> -> spec.bind(key, value.toTypedArray())
            else -> spec.bind(key, value)
        }
    }

    private fun isNotMapValue(value: Any?): Boolean =
        value !is Map<*, *>
}

