package com.kstd.application.common.response

class MessageResponse<T> private constructor(
    val message: String,
    val body: T? = null,
) {
    companion object {
        fun <T> ok(body: T? = null): MessageResponse<T> {
            return MessageResponse(
                message = "ok",
                body = body,
            )
        }

        fun <T> error(message: String = ""): MessageResponse<T> {
            return MessageResponse(
                message = message,
            )
        }
    }
}
