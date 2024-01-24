package org.example.auth.payload.responses

import java.io.Serializable

data class JwtResponse(
    var accessToken: String,
    var refreshToken: String,
    var id: Long,
    var username: String,
    var email: String,
    val roles: List<String>
): Serializable {
    var tokenType: String = "Bearer"
}