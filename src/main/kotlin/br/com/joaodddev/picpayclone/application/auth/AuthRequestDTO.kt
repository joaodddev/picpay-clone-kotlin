package br.com.joaodddev.picpayclone.application.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AuthRequestDTO(

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password is required")
    val password: String
)