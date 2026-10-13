package dev.fedormaleev.s3cloudstorage.interfaces.storage.dto

data class StorageQuotaResponse(
    val limitBytes: Long,
    val usedBytes: Long,
    val remainingBytes: Long
)