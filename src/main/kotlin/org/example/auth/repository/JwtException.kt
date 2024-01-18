package org.example.auth.repository

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class JwtException(token: String?, message: String?) :
    RuntimeException(String.format("Failed for [%s]: %s", token, message)) {
    companion object {
        private const val serialVersionUID = 1L
    }
}