package dev.fedormaleev.s3cloudstorage.application.storage

import dev.fedormaleev.s3cloudstorage.infrastructure.common.error.UserNotFoundInDb
import dev.fedormaleev.s3cloudstorage.infrastructure.persistence.UserEntity
import dev.fedormaleev.s3cloudstorage.infrastructure.persistence.UserRepository
import dev.fedormaleev.s3cloudstorage.application.storage.dto.StorageQuotaResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.Optional
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StorageServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var storageService: StorageService

    private val userId = UUID.randomUUID()
    private val limit = 1000L

    @BeforeEach
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        storageService = StorageService(userRepository)
    }

    private fun createUser(
        used: Long = 0L,
        reserved: Long = 0L
    ) = UserEntity(
        id = userId,
        login = "testuser",
        password = "hashed-password",
        usedBytes = used,
        reservedBytes = reserved
    )

    private fun mockUser(
        used: Long = 0L,
        reserved: Long = 0L,
        storageLimit: Long? = limit
    ) {
        `when`(userRepository.findById(userId))
            .thenReturn(Optional.of(createUser(used, reserved)))

        `when`(userRepository.findStorageLimitByUserId(userId))
            .thenReturn(storageLimit)
    }

    @Test
    fun `should return correct quota for existing user`() {
        mockUser(used = 300L, reserved = 100L)

        val result = storageService.getQuota(userId)

        assertEquals(
            StorageQuotaResponse(
                limitBytes = 1000L,
                usedBytes = 300L,
                remainingBytes = 600L
            ),
            result
        )
    }

    @Test
    fun `should return full quota for empty storage`() {
        mockUser()

        val result = storageService.getQuota(userId)

        assertEquals(1000L, result.remainingBytes)
        assertEquals(0L, result.usedBytes)
    }

    @Test
    fun `should subtract reserved bytes from remaining quota`() {
        mockUser(used = 0L, reserved = 250L)

        val result = storageService.getQuota(userId)

        assertEquals(750L, result.remainingBytes)
    }

    @Test
    fun `should return zero when storage is full`() {
        mockUser(used = 1000L)

        val result = storageService.getQuota(userId)

        assertEquals(0L, result.remainingBytes)
    }

    @Test
    fun `should never return negative remaining bytes`() {
        mockUser(used = 900L, reserved = 300L)

        val result = storageService.getQuota(userId)

        assertEquals(0L, result.remainingBytes)
    }

    @Test
    fun `should throw exception when user does not exist`() {
        `when`(userRepository.findById(userId))
            .thenReturn(Optional.empty())

        assertFailsWith<UserNotFoundInDb> {
            storageService.getQuota(userId)
        }

        verify(userRepository, never())
            .findStorageLimitByUserId(userId)
    }

    @Test
    fun `should return zero limit when storage plan is missing`() {
        mockUser(used = 100L, storageLimit = null)

        val result = storageService.getQuota(userId)

        assertEquals(0L, result.limitBytes)
        assertEquals(0L, result.remainingBytes)
    }
}
