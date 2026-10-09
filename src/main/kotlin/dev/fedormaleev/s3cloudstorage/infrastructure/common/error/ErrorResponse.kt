package dev.fedormaleev.s3cloudstorage.infrastructure.common.error

data class ErrorResponse(
    val status: Int,
    val message: String,
)
