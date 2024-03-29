package org.example.auth.jwt

import io.jsonwebtoken.*
import org.example.auth.dao.Users
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtTokenRepo {
    private val logger: Logger = LoggerFactory.getLogger(JwtTokenRepo::class.java)

    @Value("\${jwt.secret}")
    private val secret: String? = null

    @Value("\${jwt.expirationMs}")
    private val jwtExpirationMs = 0

    fun generateJwtToken(userDetails: Users): String {
        return generateTokenFromUsername(userDetails.username)
    }

    fun generateTokenFromUsername(username: String?): String {
        return Jwts.builder().setSubject(username).setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    fun getUserNameFromJwtToken(token: String?): String {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).body.subject
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(secret).build().parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }

        return false
    }
}