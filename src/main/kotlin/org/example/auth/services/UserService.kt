package org.example.auth.services

import org.example.auth.dao.Users
import org.example.auth.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService : UserDetailsService {
    @Autowired
    private val userRepo: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: Users = userRepo?.findUserByName(username)
            ?: throw UsernameNotFoundException("User Not Found with username: $username")

        return UserDetailsImpl.build(user)

    }
}