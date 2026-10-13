package dev.fedormaleev.s3cloudstorage.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID
import dev.fedormaleev.s3cloudstorage.infrastructure.persistence.UserEntity
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<UserEntity, UUID>{
    fun findByLogin(login: String): UserEntity?
    fun existsByLogin(login: String): Boolean

    @Query(
        value = """
        SELECT sp.limit_bytes
        FROM users u
        JOIN storage_plans sp ON u.plan = sp.code
        WHERE u.id = :userId
    """,
        nativeQuery = true
    )
    fun findStorageLimitByUserId(userId: UUID): Long?
}