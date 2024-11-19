package com.kstd.application.common.exception

import com.kstd.application.common.response.MessageResponse
import com.kstd.common.exception.KstdException
import com.kstd.common.helper.log
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlers {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    fun exceptionHandler(e: Exception): MessageResponse<Any?> {
        log.error(e) { "[ExceptionHandlers]" }
        return MessageResponse.error(e.message ?: HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
    }

    @ExceptionHandler(KstdException::class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    fun notFoundExceptionHandler(e: KstdException): MessageResponse<Any?> {
        log.warn(e) { "[ExceptionHandlers]" }
        return MessageResponse.error(e.message ?: HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
    }
}