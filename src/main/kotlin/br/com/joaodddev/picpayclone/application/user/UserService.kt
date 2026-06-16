package br.com.joaodddev.picpayclone.application.user

import br.com.joaodddev.picpayclone.domain.user.User
import br.com.joaodddev.picpayclone.infrastructure.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createUser(request: UserRequestDTO): UserResponseDTO {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already in use")
        }
        if (userRepository.existsByDocument(request.document)) {
            throw IllegalArgumentException("Document already in use")
        }

        val user = User(
            fullName = request.fullName,
            document = request.document,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            balance = request.balance,
            userType = request.userType
        )

        val saved = userRepository.save(user)
        return UserResponseDTO.fromEntity(saved)
    }

    fun getAllUsers(): List<UserResponseDTO> {
        return userRepository.findAll().map { UserResponseDTO.fromEntity(it) }
    }

    fun getUserById(id: Long): UserResponseDTO {
        val user = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User not found with id: $id") }
        return UserResponseDTO.fromEntity(user)
    }

    fun updateUser(id: Long, request: UserRequestDTO): UserResponseDTO {
        val existing = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User not found with id: $id") }

        val updated = existing.copy(
            fullName = request.fullName,
            document = request.document,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            balance = request.balance,
            userType = request.userType
        )

        val saved = userRepository.save(updated)
        return UserResponseDTO.fromEntity(saved)
    }

    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw NoSuchElementException("User not found with id: $id")
        }
        userRepository.deleteById(id)
    }
}