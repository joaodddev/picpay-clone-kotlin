package br.com.joaodddev.picpayclone.application.auth

data class AuthResponseDTO(
    val token: String,
    val email: String,
    val userType: String
)