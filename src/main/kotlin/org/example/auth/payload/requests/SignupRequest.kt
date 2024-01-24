package org.example.auth.payload.requests

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.io.Serializable

class SignupRequest: Serializable {
    @Size(min = 3, max = 20)
    var username: @NotBlank String? = null

    @Size(max = 50)
    var email: @NotBlank @Email String? = null

    var role: Set<String>? = null

    @Size(min = 6, max = 40)
    var password: @NotBlank String? = null
}
