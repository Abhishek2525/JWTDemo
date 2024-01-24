package org.example.auth.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import org.springframework.http.MediaType;

@Component
class JwtAuthMain : AuthenticationEntryPoint {
    private val logger: Logger = LoggerFactory.getLogger(JwtAuthMain::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        logger.error("Unauthorized error: {}", authException.message)
//
        response.contentType = MediaType.APPLICATION_JSON_VALUE
//        response.status = HttpServletResponse.SC_UNAUTHORIZED
//
//        val body: MutableMap<String, Any?> = HashMap()
//        body["status"] = HttpServletResponse.SC_UNAUTHORIZED
//        body["error"] = "Unauthorized"
//        body["message"] = authException.message
//        body["path"] = request.servletPath
//
//        val mapper = ObjectMapper()
//        mapper.writeValue(response.outputStream, body)

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}