package dev.fedormaleev.s3cloudstorage.infrastructure.security.jwt

import dev.fedormaleev.s3cloudstorage.infrastructure.security.CustomUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader =
            request.getHeader("Authorization")

        if (
            authHeader == null ||
            !authHeader.startsWith("Bearer ")
        ) {
            filterChain.doFilter(request, response)
            return
        }

        val token =
            authHeader.substring(7)

        try {
            val username =
                jwtService.extractUsername(token)

            if (
                SecurityContextHolder
                    .getContext()
                    .authentication == null
            ) {

                val userDetails =
                    userDetailsService
                        .loadUserByUsername(username)

                val authentication =
                    UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )

                SecurityContextHolder
                    .getContext()
                    .authentication = authentication
            }

        } catch (ex: Exception) {
            // invalid / expired JWT
        }

        filterChain.doFilter(
            request,
            response
        )
    }
}