package dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto

import jakarta.validation.constraints.NotBlank

data class LoginRequest(

    @field:NotBlank(message = "Login is required")
    val login: String,

    @field:NotBlank(message = "Password is required")
    val password: String
)