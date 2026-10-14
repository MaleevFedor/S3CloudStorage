package dev.fedormaleev.s3cloudstorage.application.storage

import dev.fedormaleev.s3cloudstorage.infrastructure.common.error.UserNotFoundInDb
import dev.fedormaleev.s3cloudstorage.infrastructure.persistence.UserRepository
import dev.fedormaleev.s3cloudstorage.application.storage.dto.StorageQuotaResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

//ToDo add tests
@Service
class StorageService(
    private val userRepository: UserRepository
) {
    private val logger = LoggerFactory.getLogger(StorageService::class.java)

    fun getQuota(userId: UUID): StorageQuotaResponse {
        logger.info("Getting quota for user $userId")

        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundInDb("Could not find user with id $userId") }

        logger.info("User successfully found in database for user $userId")

        val used = user.usedBytes
        val reserved = user.reservedBytes
        val limit = userRepository.findStorageLimitByUserId(userId) ?: 0

        logger.info("User limit is $limit")

        return StorageQuotaResponse(
            limitBytes = limit,
            usedBytes = used,
            remainingBytes = (limit - used - reserved).coerceAtLeast(0L)
        )
    }
}