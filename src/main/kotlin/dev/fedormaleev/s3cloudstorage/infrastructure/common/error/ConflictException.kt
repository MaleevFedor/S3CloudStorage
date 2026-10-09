package dev.fedormaleev.s3cloudstorage.infrastructure.common.error

open class ConflictException(
    message: String,
) : RuntimeException(message)