package com.example.noticeservice.shared.exceptions

import mu.KotlinLogging

private val log = KotlinLogging.logger {}

/**
 * A reusable base class for lightweight, single-line exceptions.
 * - Logs automatically upon creation.
 * - Suppresses expensive stack-trace generation.
 * - Prints concise one-line messages.
 *
 * @param message The error message to log and include in the exception.
 * @param cause Optional cause of the exception.
 */
open class BaseSingleLineException(
    message: String,
) : RuntimeException(message, null) {

    init {
        log.error { toMessage() }
    }

    fun toMessage(): String {
        return "${this::class.simpleName}: $message"
    }

    /**
     * Suppress stack trace creation to keep logs clean and avoid performance costs.
     */
    override fun fillInStackTrace(): Throwable = this

    /**
     * Ensure clean single-line toString() representation.
     */
    override fun toString(): String = toMessage()
}
