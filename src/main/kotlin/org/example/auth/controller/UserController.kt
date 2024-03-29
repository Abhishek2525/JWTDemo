package org.example.auth.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@Component
class UserController {

    @GetMapping("/")
    fun allAccess(): String {
        return " For all data - api/user/all\n For dev data - api/user/dev\n For profile - api/user/1"
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    fun getAllUsers(): List<String> {
        val list = listOf("user1", "user2", "user3", "user4", "user5", "dev1", "dev2", "dev3")
        return list
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    fun getAllDevs(): List<String> {
        val list = listOf("test1", "test2", "test3")
        return list
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun getProfile(): List<String> {
        val list = listOf("user")
        return list
    }

}