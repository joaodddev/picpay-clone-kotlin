package br.com.joaodddev.picpayclone.infrastructure.user

import br.com.joaodddev.picpayclone.application.user.UserRequestDTO
import br.com.joaodddev.picpayclone.application.user.UserResponseDTO
import br.com.joaodddev.picpayclone.application.user.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Gerenciamento de usuários")
@SecurityRequirement(name = "Bearer Authentication")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário na plataforma")
    fun createUser(@Valid @RequestBody request: UserRequestDTO): ResponseEntity<UserResponseDTO> {
        val response = userService.createUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Retorna todos os usuários cadastrados")
    fun getAllUsers(): ResponseEntity<List<UserResponseDTO>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponseDTO> {
        return ResponseEntity.ok(userService.getUserById(id))
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    fun updateUser(
        @PathVariable id: Long,
        @Valid @RequestBody request: UserRequestDTO
    ): ResponseEntity<UserResponseDTO> {
        return ResponseEntity.ok(userService.updateUser(id, request))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}