package br.com.joaodddev.picpayclone.application.transaction

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class TransactionRequestDTO(

    @field:NotNull(message = "Sender id is required")
    val senderId: Long,

    @field:NotNull(message = "Receiver id is required")
    val receiverId: Long,

    @field:NotNull(message = "Amount is required")
    @field:DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    val amount: BigDecimal
)