package com.example.noticeservice.api.shared.repository

import com.example.noticeservice.api.shared.exception.RepositoryExtraFieldsApiException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Mono

fun snakeToCamel(snake: String): String {
    return snake.split("_").joinToString("") { part ->
        part.replaceFirstChar { it.uppercaseChar() }
    }.replaceFirstChar { it.lowercaseChar() }
}

class GenericPatchRepository<T : Any>(
    private val databaseClient: DatabaseClient,
    private val objectMapper: ObjectMapper,

    private val tableName: String,
    private val columnTypes: Map<String, Class<*>>,
) {

    fun patchById(id: Long, fields: Map<String, Any?>, entityClass: Class<T>, allowedFields: Set<String>): Mono<T> {
        if (fields.isEmpty()) return Mono.empty()

        // Remove nested objects and id
        val primitiveFields = fields
            .filterValues { it !is Map<*, *> }
            .filterKeys { it != "id" }

        if (primitiveFields.isEmpty()) return Mono.empty()

        // Check for forbidden keys
        val extraFields = primitiveFields
            .filterKeys { it !in allowedFields }

        if (extraFields.isNotEmpty()) {
            return Mono.error(
                RepositoryExtraFieldsApiException(
                    GenericPatchRepository::class,
                    extraFields,
                    allowedFields
                )
            )
        }

        // Build SQL
        val setClause = primitiveFields.keys.joinToString(", ") { "$it = :$it" }
        val sql = "UPDATE $tableName SET $setClause WHERE id = :id RETURNING *"

        var spec = databaseClient.sql(sql).bind("id", id)

        primitiveFields.forEach { (key, value) ->
            val columnType = columnTypes[key]
            spec = when {
                columnType == null -> spec
                value == null -> spec.bindNull(key, columnType)
                value is Collection<*> -> spec.bind(key, value.toTypedArray())
                else -> spec.bind(key, value)
            }
        }

        return spec.map { row, meta ->
            val result = mutableMapOf<String, Any?>()

            meta.columnMetadatas.forEach { col ->
                val snake = col.name
                val camel = snakeToCamel(snake)
                val type = columnTypes[snake] ?: Any::class.java
                result[camel] = row.get(snake, type)
            }

            objectMapper.convertValue(result, entityClass)
        }.one()
    }
}
