package dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto

import dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto.InternalLoginResponse

data class LoginResponse(
    val status: Int,
    val message: String,
    val userInfo: InternalLoginResponse? = null,
)