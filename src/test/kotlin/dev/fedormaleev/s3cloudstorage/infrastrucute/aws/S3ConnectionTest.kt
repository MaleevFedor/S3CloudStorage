package dev.fedormaleev.s3cloudstorage.infrastrucute.aws

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import software.amazon.awssdk.services.s3.S3Client

@SpringBootTest
@EnabledIfEnvironmentVariable(
    named = "RUN_AWS_TESTS",
    matches = "true"
)
class S3ConnectionTest(
    @Autowired private val s3Client: S3Client,

    @Value("\${aws.s3.bucket}")
    private val bucket: String
) {

    @Test
    fun `should connect to S3 bucket`() {
        s3Client.headBucket {
            it.bucket(bucket)
        }
    }
}