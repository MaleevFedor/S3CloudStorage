package dev.fedormaleev.s3cloudstorage.infrastructure.security.auth

import dev.fedormaleev.s3cloudstorage.domain.model.user.Role
import dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto.InternalLoginResponse
import dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.dto.UserResponse
import dev.fedormaleev.s3cloudstorage.infrastructure.persistence.UserEntity
import dev.fedormaleev.s3cloudstorage.infrastructure.persistence.UserRepository
import dev.fedormaleev.s3cloudstorage.infrastructure.security.SecurityUser
import dev.fedormaleev.s3cloudstorage.infrastructure.security.auth.errors.UserAlreadyExistsException
import dev.fedormaleev.s3cloudstorage.infrastructure.security.jwt.JwtService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val logger: Logger = LoggerFactory.getLogger(AuthService::class.java),
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {

    fun login(
        login: String,
        password: String
    ): InternalLoginResponse {

        val authentification = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                login,
                password
            )
        )

        val userDetails = authentification.principal as SecurityUser

        val token = jwtService.generateToken(authentification.name)
        return InternalLoginResponse(
            UserResponse(userDetails.getUserId(), userDetails.getUsername(), userDetails.getRole().toString()),
            token
        )
    }

    fun createUser(
        login: String,
        password: String
    ){
        if (userRepository.existsByLogin(login)) {
            throw UserAlreadyExistsException(login)
        }

        val hashedPassword = passwordEncoder.encode(password)

        if (hashedPassword == null){
            logger.info("User does not have password")
            throw IllegalArgumentException("Password hashing error")
        }

        val user = UserEntity(
            id = UUID.randomUUID(),
            login = login,
            password = hashedPassword,
            role = Role.USER
        )

        userRepository.save(user)

        val authentification = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                login,
                password
            )
        )
    }

}