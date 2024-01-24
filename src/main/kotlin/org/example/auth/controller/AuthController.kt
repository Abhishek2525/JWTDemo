package org.example.auth.controller


import jakarta.validation.Valid
import org.example.auth.dao.Role
import org.example.auth.dao.Token
import org.example.auth.dao.User
import org.example.auth.jwt.JwtTokenRepo
import org.example.auth.payload.requests.LoginRequest
import org.example.auth.payload.requests.RefreshTokenRequest
import org.example.auth.payload.requests.SignupRequest
import org.example.auth.payload.responses.JwtResponse
import org.example.auth.payload.responses.JwtTokeRefreshResponse
import org.example.auth.payload.responses.Response
import org.example.auth.repository.JwtException
import org.example.auth.repository.RoleRepository
import org.example.auth.repository.UserRepository
import org.example.auth.services.RefreshTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer
import kotlin.jvm.optionals.getOrNull


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtUtils: JwtTokenRepo

    @Autowired
    lateinit var refreshTokenService: RefreshTokenService


    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest?): ResponseEntity<*> {
        try {
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest?.username,
                    loginRequest?.password
                )
            )

            SecurityContextHolder.getContext().authentication = authentication
            val data = SecurityContextHolder.getContext().authentication.principal as UserDetails
            val userDetails = userRepository.findByUsername(data.username)
            return userDetails?.getOrNull()?.let {
                val jwt = jwtUtils.generateJwtToken(it)

                val refreshToken: Token = refreshTokenService.createRefreshToken(it.id)

                ResponseEntity.ok(
                    JwtResponse(
                        jwt, refreshToken.token.orEmpty(), it.id,
                        it.username.orEmpty(), it.password.orEmpty(), emptyList()
                    )
                )
            } ?: ResponseEntity.ok(
                "Failed to generate token"
            )

        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: @Valid SignupRequest?): ResponseEntity<*> {
        if (userRepository.existsByUsername(signUpRequest?.username) == true) {
            return ResponseEntity.badRequest().body<Any>(Response(message = "Error: Username is already taken!", null))
        }

        if (userRepository.existsByEmail(signUpRequest?.email) == true) {
            return ResponseEntity.badRequest().body<Any>(Response(message = "Error: Email is already in use!", null))
        }

        // Create new user's account
        val user = User(
            signUpRequest?.username, signUpRequest?.email,
            encoder.encode(signUpRequest?.password),
        )

        val strRoles = signUpRequest?.role.orEmpty()
        val roles: MutableSet<Role> = HashSet()

        strRoles.forEach(Consumer { role: String? ->
            when (role) {
                "ADMIN" -> {
                    val adminRole: Role = roleRepository.findByName("ADMIN")
                    roles.add(adminRole)
                }

                else -> {
                    val userRole: Role = roleRepository.findByName("USER")
                    roles.add(userRole)
                }
            }
        })

        user.roles = roles
        userRepository.save(user)

        return ResponseEntity.ok(Response("User registered successfully!", null))
    }

    @PostMapping("/refreshToken")
    fun refreshtoken(@RequestBody request: @Valid RefreshTokenRequest?): ResponseEntity<*> {
        val requestRefreshToken: String = request?.refreshToken.orEmpty()
        val resp = refreshTokenService.findUserByToken(requestRefreshToken)
            ?.map { token -> refreshTokenService.verifyExpiration(token!!) }
            ?.map(Token::user)
            ?.map { user ->
                val token = jwtUtils.generateTokenFromUsername(user?.username)
                ResponseEntity.ok(JwtTokeRefreshResponse(token, requestRefreshToken))
            }?.orElseThrow {
                JwtException(
                    requestRefreshToken,
                    "Refresh token is not in database!"
                )
            } ?: ResponseEntity.ok<Response>(Response("Refresh token is not in database!", null))
        return resp
    }

    @PostMapping("/signout")
    fun logoutUser(): ResponseEntity<Response> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val users = userRepository.findByUsername(userDetails.username)
        return users?.getOrNull()?.let {
            refreshTokenService.deleteByUserId(it.id)
            ResponseEntity.ok(Response("Log out successful!", null))
        } ?: ResponseEntity.ok(Response("Failed to log out", null))

    }

}