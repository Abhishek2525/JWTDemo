package org.example.auth.payload.requests

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

data class LoginRequest(
    var username: @NotBlank String? = null,
    var password: @NotBlank String? = null
): Serializable