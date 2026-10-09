package dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto

data class AddUserResponse(
    val login: String,
    val message: String,
)
