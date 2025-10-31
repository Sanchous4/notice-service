package com.example.noticeservice.api.shared.handler

import com.example.noticeservice.api.shared.exception.ApiBaseException
import com.example.noticeservice.api.shared.response.ApiErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Order(-2)
@Configuration
class GlobalReactiveErrorHandler(
    private val objectMapper: ObjectMapper
) : ErrorWebExceptionHandler {

    private val logger = KotlinLogging.logger {}

    private fun findRootCause(exception: Throwable): Throwable {
        var depth = 0
        var cause = exception
        while (cause.cause != null && cause.cause !== cause && depth++ < 10) {
            cause = cause.cause!!
        }
        return cause
    }

    private fun buildError(exchange: ServerWebExchange, cause: Throwable): Pair<HttpStatus, ApiErrorResponse> {
        val defaultPath = exchange.request.path.value()

        return if (cause is ApiBaseException) {
            cause.httpStatus to cause.toApiResponse(defaultPath)
        } else {
            val status = HttpStatus.INTERNAL_SERVER_ERROR

            status to ApiErrorResponse.of(
                status,
                message = cause.message ?: "Unknown server error",
                path = defaultPath
            )
        }
    }

    override fun handle(exchange: ServerWebExchange, exception: Throwable): Mono<Void> {
        logger.error { exception.message }

        val response = exchange.response
        if (response.isCommitted) return Mono.error(exception)
        response.headers.contentType = MediaType.APPLICATION_JSON

        val rootCause = findRootCause(exception)
        val (status, apiResponse) = buildError(exchange, rootCause)
        response.statusCode = status

        val jsonBytes = objectMapper.writeValueAsBytes(apiResponse)
        val buffer = response.bufferFactory().wrap(jsonBytes)
        return response.writeWith(Mono.just(buffer))
    }
}
