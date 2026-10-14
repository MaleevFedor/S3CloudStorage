package dev.fedormaleev.s3cloudstorage.interfaces.storage

import dev.fedormaleev.s3cloudstorage.application.storage.StorageService
import dev.fedormaleev.s3cloudstorage.infrastructure.security.SecurityUser
import dev.fedormaleev.s3cloudstorage.application.storage.dto.StorageQuotaResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/me")
class MeController(
    private val storageService: StorageService
) {
    @GetMapping("/storage-limit")
    fun getMyStorage(
        @AuthenticationPrincipal user: SecurityUser
    ): StorageQuotaResponse {
        return storageService.getQuota(user.getUserId())
    }
}