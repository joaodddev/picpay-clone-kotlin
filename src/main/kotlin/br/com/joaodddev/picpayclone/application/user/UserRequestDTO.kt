package br.com.joaodddev.picpayclone.application.user

import br.com.joaodddev.picpayclone.domain.user.UserType
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class UserRequestDTO(

    @field:NotBlank(message = "Full name is required")
    val fullName: String,

    @field:NotBlank(message = "Document is required")
    val document: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password is required")
    val password: String,

    val balance: BigDecimal = BigDecimal.ZERO,

    @field:NotNull(message = "User type is required")
    val userType: UserType
)