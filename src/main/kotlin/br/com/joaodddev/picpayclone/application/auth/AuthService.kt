package br.com.joaodddev.picpayclone.application.auth

import br.com.joaodddev.picpayclone.infrastructure.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService
) {

    fun login(request: AuthRequestDTO): AuthResponseDTO {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )

        val userDetails = userDetailsService.loadUserByUsername(request.email)
        val token = jwtService.generateToken(userDetails.username)

        val role = userDetails.authorities.first().authority
            .removePrefix("ROLE_")

        return AuthResponseDTO(
            token = token,
            email = userDetails.username,
            userType = role
        )
    }
}