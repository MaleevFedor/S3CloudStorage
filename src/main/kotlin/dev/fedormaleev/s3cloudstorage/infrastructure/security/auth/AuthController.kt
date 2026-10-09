package dev.fedormaleev.s3cloudstorage.infrastructure.security.auth

import dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto.LoginResponse
import dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto.AddUserRequest
import dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto.AddUserResponse
import dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto.LoginRequest
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest
    ) : ResponseEntity<LoginResponse> {
        logger.info("Login request received for user: {}", request.login)

        val userDetails = authService.login(
            request.login,
            request.password
        )

        logger.info("User successfully logged in: {}", request.login)
        val response = LoginResponse(200, "Successfully logged in", userDetails)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody request : AddUserRequest
    ) : ResponseEntity<AddUserResponse> {
        logger.info("Adding a user: {}", request.login)
        authService.createUser(request.login, request.password)
        logger.info("User successfully registered: {}", request.login)
        val response = AddUserResponse(request.login, request.password)
        return ResponseEntity.ok(response)
    }
}