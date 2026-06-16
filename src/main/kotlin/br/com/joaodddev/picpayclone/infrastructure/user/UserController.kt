package br.com.joaodddev.picpayclone.infrastructure.user

import br.com.joaodddev.picpayclone.application.user.UserRequestDTO
import br.com.joaodddev.picpayclone.application.user.UserResponseDTO
import br.com.joaodddev.picpayclone.application.user.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun createUser(@Valid @RequestBody request: UserRequestDTO): ResponseEntity<UserResponseDTO> {
        val response = userService.createUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponseDTO>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponseDTO> {
        return ResponseEntity.ok(userService.getUserById(id))
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @Valid @RequestBody request: UserRequestDTO
    ): ResponseEntity<UserResponseDTO> {
        return ResponseEntity.ok(userService.updateUser(id, request))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}