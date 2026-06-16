package br.com.joaodddev.picpayclone.domain.user

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "users")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val fullName: String,

    @Column(nullable = false, unique = true)
    val document: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val balance: BigDecimal = BigDecimal.ZERO,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val userType: UserType = UserType.COMMON
)

enum class UserType {
    COMMON,
    MERCHANT
}