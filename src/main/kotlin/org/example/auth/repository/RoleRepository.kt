package org.example.auth.repository

import org.example.auth.common.Roles
import org.example.auth.dao.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role?, Long?> {
    fun findByName(name: Roles?): Optional<Role?>?
}