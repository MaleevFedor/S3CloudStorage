package dev.fedormaleev.s3cloudstorage.infrastructure.common.error

class UserNotFoundInDb(
    message: String,
) : RuntimeException(message)