package dev.fedormaleev.s3cloudstorage.infrastructure.persistence

import dev.fedormaleev.s3cloudstorage.domain.model.file.FileStatus
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "files")
class FileEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID? = null,

    @Column(name = "user_id", nullable = false)
    var userId: UUID,

    @Column(name = "original_name", nullable = false, length = 255)
    var originalName: String,

    @Column(name = "s3_key", nullable = false, unique = true, length = 1024)
    var s3Key: String,

    @Column(name = "content_type", nullable = false, length = 255)
    var contentType: String,

    @Column(name = "size_bytes", nullable = false)
    var sizeBytes: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    var status: FileStatus = FileStatus.PENDING,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant? = null
)
