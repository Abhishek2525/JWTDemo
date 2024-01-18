package org.example.auth.payload.responses

data class JwtResponse(
    var accessToken: String,
    var refreshToken: String,
    var id: Long,
    var username: String,
    var email: String,
    val roles: List<String>
){
    var tokenType: String = "Bearer"
}