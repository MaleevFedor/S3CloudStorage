package dev.fedormaleev.s3cloudstorage.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID
import dev.fedormaleev.s3cloudstorage.infrastructure.persistence.UserEntity

interface UserRepository : JpaRepository<UserEntity, UUID>{
    fun findByLogin(login: String): UserEntity?
    fun existsByLogin(login: String): Boolean
}