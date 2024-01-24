package org.example.auth.payload.responses

import java.io.Serializable

data class Response(val message: String?, val data: Any?): Serializable
