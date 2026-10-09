package dev.fedormaleev.s3cloudstorage.infrastructure.common.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException::class)
    fun handleUserAlreadyExists(
        ex: ConflictException
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(
                ErrorResponse(
                    status = 409,
                    message = ex.message ?: "Conflict occured"
                )
            )
    }
}