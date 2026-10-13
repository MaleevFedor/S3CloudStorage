
package dev.fedormaleev.s3cloudstorage.application.file.port

interface FileStorage {
    fun createUploadUrl(
        key: String,
        contentType: String
    ): String
}
