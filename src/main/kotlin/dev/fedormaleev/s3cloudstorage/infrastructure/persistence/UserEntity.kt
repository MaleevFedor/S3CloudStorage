package dev.fedormaleev.s3cloudstorage.infrastructure.persistence

import dev.fedormaleev.s3cloudstorage.domain.model.user.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    val id: UUID,

    @Column(name = "login", nullable = false)
    val login: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var role: Role = Role.USER,

    @Column(name = "used_bytes", nullable = false)
    var usedBytes: Long = 0L,

    @Column(name = "reserved_bytes", nullable = false)
    var reservedBytes: Long = 0L,

){
}