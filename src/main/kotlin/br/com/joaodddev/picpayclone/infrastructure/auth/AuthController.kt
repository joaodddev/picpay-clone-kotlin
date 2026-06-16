package br.com.joaodddev.picpayclone.infrastructure.auth

import br.com.joaodddev.picpayclone.application.auth.AuthRequestDTO
import br.com.joaodddev.picpayclone.application.auth.AuthResponseDTO
import br.com.joaodddev.picpayclone.application.auth.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: AuthRequestDTO): ResponseEntity<AuthResponseDTO> {
        return ResponseEntity.ok(authService.login(request))
    }
}