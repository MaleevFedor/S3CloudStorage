package dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto

import java.util.UUID

data class UserResponse(
    val id: UUID,
    val username: String,
    val role: String,
)