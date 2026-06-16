package br.com.joaodddev.picpayclone.application.user

import br.com.joaodddev.picpayclone.domain.user.User
import br.com.joaodddev.picpayclone.domain.user.UserType
import java.math.BigDecimal

data class UserResponseDTO(
    val id: Long?,
    val fullName: String,
    val document: String,
    val email: String,
    val balance: BigDecimal,
    val userType: UserType
) {
    companion object {
        fun fromEntity(user: User): UserResponseDTO {
            return UserResponseDTO(
                id = user.id,
                fullName = user.fullName,
                document = user.document,
                email = user.email,
                balance = user.balance,
                userType = user.userType
            )
        }
    }
}