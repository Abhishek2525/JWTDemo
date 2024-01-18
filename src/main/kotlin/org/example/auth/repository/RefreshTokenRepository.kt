package org.example.auth.repository

import org.example.auth.dao.Token
import org.example.auth.dao.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface RefreshTokenRepository : JpaRepository<Token?, Long?> {
    fun findUserByToken(token: String?): Optional<Token?>?

    @Modifying
    fun deleteByUser(user: Users?): Int
}