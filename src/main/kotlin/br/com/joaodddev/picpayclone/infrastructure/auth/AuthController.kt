package br.com.joaodddev.picpayclone.infrastructure.auth

import br.com.joaodddev.picpayclone.application.auth.AuthRequestDTO
import br.com.joaodddev.picpayclone.application.auth.AuthResponseDTO
import br.com.joaodddev.picpayclone.application.auth.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Autenticação de usuários")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica o usuário e retorna um token JWT")
    fun login(@Valid @RequestBody request: AuthRequestDTO): ResponseEntity<AuthResponseDTO> {
        return ResponseEntity.ok(authService.login(request))
    }
}