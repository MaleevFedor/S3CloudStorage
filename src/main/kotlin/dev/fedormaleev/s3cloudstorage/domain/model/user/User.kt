package dev.fedormaleev.s3cloudstorage.domain.model.user

import jakarta.persistence.*
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
    var role: Role = Role.USER
){
}