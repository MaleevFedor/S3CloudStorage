package dev.fedormaleev.s3cloudstorage.domain.model.pricing

enum class StoragePlan {
    FREE, // 10737418240 bytes
    PLUS, // 107374182400 bytes
    MAX // 1099511627776 bytes
}