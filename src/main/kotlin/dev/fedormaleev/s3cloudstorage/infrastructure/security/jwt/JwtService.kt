package dev.fedormaleev.s3cloudstorage.infrastructure.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}")
    private val secret: String,

    @Value("\${jwt.expiration-ms}")
    private val expirationMs: Long
) {

    private fun signingKey(): SecretKey =
        Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(secret)
        )

    fun generateToken(username: String): String {
        val now = Date()

        return Jwts.builder()
            .subject(username)
            .issuedAt(now)
            .expiration(
                Date(now.time + expirationMs)
            )
            .signWith(signingKey())
            .compact()
    }

    fun extractUsername(token: String): String {
        return Jwts.parser()
            .verifyWith(signingKey())
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }
}