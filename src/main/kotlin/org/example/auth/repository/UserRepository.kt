package org.example.auth.repository

import org.example.auth.dao.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<Users?, Int?> {
    fun findUserByName(username: String?): Users?
    fun checkUserExists(username: String?): Boolean?
    fun checkUserExistsByEmail(email: String?): Boolean?
}