package br.com.joaodddev.picpayclone.domain.transaction

import br.com.joaodddev.picpayclone.domain.user.User
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
data class Transaction(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    val sender: User,

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    val receiver: User,

    @Column(nullable = false)
    val amount: BigDecimal,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)