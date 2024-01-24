package org.example.auth.services

import jakarta.transaction.Transactional
import org.example.auth.dao.Token
import org.example.auth.repository.JwtException
import org.example.auth.repository.RefreshTokenRepository
import org.example.auth.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class RefreshTokenService {
    @Value("\${jwt.refreshExpirationMs}")
    var refreshTokenDurationMs: Long = 0

    @Autowired
    lateinit var refreshTokenRepository: RefreshTokenRepository

    @Autowired
    lateinit var userRepository: UserRepository

    fun findUserByToken(token: String?): Optional<Token?>? {
        return refreshTokenRepository.findUserByToken(token)
    }

    fun createRefreshToken(userId: Long): Token {
        var refreshToken = Token()
        refreshToken.user = userRepository.findById(userId.toInt()).get()
        refreshToken.expiryDate = Instant.now().plusMillis(refreshTokenDurationMs)
        refreshToken.token = UUID.randomUUID().toString()

        refreshToken = refreshTokenRepository.save(refreshToken)
        return refreshToken
    }

    fun verifyExpiration(token: Token): Token {
        if ((token.token?.compareTo(Instant.now().toString()) ?: 0) < 0) {
            refreshTokenRepository.delete(token)
            throw JwtException(token.token, "Refresh token was expired. Please make a new sign in request")
        }

        return token
    }

    @Transactional
    fun deleteByUserId(userId: Long): Int {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId.toInt()).get())
    }
}