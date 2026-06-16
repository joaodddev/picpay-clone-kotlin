package br.com.joaodddev.picpayclone.application.transaction

import br.com.joaodddev.picpayclone.domain.transaction.Transaction
import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionResponseDTO(
    val id: Long?,
    val senderId: Long?,
    val receiverId: Long?,
    val amount: BigDecimal,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(transaction: Transaction): TransactionResponseDTO {
            return TransactionResponseDTO(
                id = transaction.id,
                senderId = transaction.sender.id,
                receiverId = transaction.receiver.id,
                amount = transaction.amount,
                createdAt = transaction.createdAt
            )
        }
    }
}