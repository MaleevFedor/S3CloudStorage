package dev.fedormaleev.s3cloudstorage.infrastructure.security

import dev.fedormaleev.s3cloudstorage.infrastructure.persistence.UserEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SecurityUser(
    private val user: UserEntity
) : UserDetails {

    override fun getUsername() =
        user.login

    override fun getPassword() =
        user.password

    override fun getAuthorities() =
        listOf(
            SimpleGrantedAuthority(
                "ROLE_${user.role.name}"
            )
        )

    fun getRole() = user.role

    fun getUserId() = user.id
}