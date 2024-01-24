package org.example.auth.payload.requests

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

class RefreshTokenRequest: Serializable {
    var refreshToken: @NotBlank String? = null
}