package dev.fedormaleev.s3cloudstorage.infrastructure.aws.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
class S3Config(
    @Value("\${aws.region}")
    private val region: String
) {

    @Bean(destroyMethod = "close")
    fun s3Client(): S3Client =
        S3Client.builder()
            .region(Region.of(region))
            .build()

    @Bean(destroyMethod = "close")
    fun s3Presigner(): S3Presigner =
        S3Presigner.builder()
            .region(Region.of(region))
            .build()
}
