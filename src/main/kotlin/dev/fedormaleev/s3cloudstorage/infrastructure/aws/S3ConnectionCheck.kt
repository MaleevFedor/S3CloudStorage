package dev.fedormaleev.s3cloudstorage.infrastructure.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.S3Client
import org.slf4j.LoggerFactory

@Component
class S3ConnectionCheck(
    private val s3Client: S3Client,

    @Value("\${aws.s3.bucket}")
    private val bucket: String,
) : ApplicationRunner {

    private val logger = LoggerFactory.getLogger(S3ConnectionCheck::class.java)

    override fun run(args: ApplicationArguments) {
        logger.info("Connecting to S3 bucket $bucket")
        s3Client.headBucket {
            it.bucket(bucket)
        }

        logger.info("Successfully connected to S3!")
    }
}