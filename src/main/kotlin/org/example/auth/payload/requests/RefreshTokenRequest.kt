package org.example.auth.payload.requests

import jakarta.validation.constraints.NotBlank
class RefreshTokenRequest {
    var refreshToken: @NotBlank String? = null
}