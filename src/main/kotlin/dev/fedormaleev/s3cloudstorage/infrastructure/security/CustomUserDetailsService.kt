package dev.fedormaleev.s3cloudstorage.infrastructure.security

import dev.fedormaleev.s3cloudstorage.infrastructure.persistence.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(login: String): UserDetails {
        val user = userRepository.findByLogin(login)
            ?: throw UsernameNotFoundException("User not found")

        return SecurityUser(user)
    }
}