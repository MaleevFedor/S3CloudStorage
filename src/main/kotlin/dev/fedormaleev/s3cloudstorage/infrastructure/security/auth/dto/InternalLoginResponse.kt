package dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto

import dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto.UserResponse

data class InternalLoginResponse(
    val user: UserResponse,
    val accessToken: String,
    )