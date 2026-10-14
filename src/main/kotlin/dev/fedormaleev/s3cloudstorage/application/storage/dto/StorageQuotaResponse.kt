package dev.fedormaleev.s3cloudstorage.application.storage.dto

data class StorageQuotaResponse(
    val limitBytes: Long,
    val usedBytes: Long,
    val remainingBytes: Long
)