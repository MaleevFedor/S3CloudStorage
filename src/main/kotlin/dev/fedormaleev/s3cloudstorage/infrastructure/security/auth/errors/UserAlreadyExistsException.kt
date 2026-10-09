package dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.errors

import dev.fedormaleev.s3cloudstorage.infrastructure.common.error.ConflictException

class UserAlreadyExistsException(
    login: String
) : ConflictException(
    "User with login '$login' already exists"
)