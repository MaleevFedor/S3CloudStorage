package dev.fedormaleev.s3cloudstorage.infrastructure.aws

import dev.fedormaleev.s3cloudstorage.application.file.port.FileStorage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

@Service
class S3FileStorage(
    private val s3Presigner: S3Presigner,

    @Value("\${aws.s3.bucket}")
    private val bucket: String
) : FileStorage {

    override fun createUploadUrl(
        key: String,
        contentType: String
    ): String {

        val putRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(contentType)
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(10))
            .putObjectRequest(putRequest)
            .build()

        return s3Presigner.presignPutObject(presignRequest)
            .url()
            .toString()
    }
}
