package org.example.auth.payload.responses

import java.io.Serializable

data class JwtTokeRefreshResponse(var accessToken: String, var refreshToken: String) : Serializable {
    var tokenType: String = "Bearer"
}