package dev.fedormaleev.s3cloudstorage.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FileRepository : JpaRepository<FileEntity, UUID> {

    fun findAllByUserId(userId: UUID): List<FileEntity>

    fun findByIdAndUserId(id: UUID, userId: UUID): FileEntity?
}
