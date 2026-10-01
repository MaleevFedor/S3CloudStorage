package dev.fedormaleev.s3cloudstorage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class S3CloudStorageApplication

fun main(args: Array<String>) {
    runApplication<S3CloudStorageApplication>(*args)
}
