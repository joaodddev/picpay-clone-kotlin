package br.com.joaodddev.picpayclone.infrastructure.transaction

import br.com.joaodddev.picpayclone.application.transaction.TransactionRequestDTO
import br.com.joaodddev.picpayclone.application.transaction.TransactionResponseDTO
import br.com.joaodddev.picpayclone.application.transaction.TransactionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "Transferências entre usuários")
@SecurityRequirement(name = "Bearer Authentication")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping
    @Operation(summary = "Realizar transferência", description = "Transfere saldo entre dois usuários")
    fun transfer(@Valid @RequestBody request: TransactionRequestDTO): ResponseEntity<TransactionResponseDTO> {
        val response = transactionService.transfer(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    @Operation(summary = "Listar transferências")
    fun getAllTransactions(): ResponseEntity<List<TransactionResponseDTO>> {
        return ResponseEntity.ok(transactionService.getAllTransactions())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar transferência por ID")
    fun getTransactionById(@PathVariable id: Long): ResponseEntity<TransactionResponseDTO> {
        return ResponseEntity.ok(transactionService.getTransactionById(id))
    }

    @GetMapping("/sender/{senderId}")
    @Operation(summary = "Transferências por remetente")
    fun getBySender(@PathVariable senderId: Long): ResponseEntity<List<TransactionResponseDTO>> {
        return ResponseEntity.ok(transactionService.getTransactionsBySender(senderId))
    }

    @GetMapping("/receiver/{receiverId}")
    @Operation(summary = "Transferências por destinatário")
    fun getByReceiver(@PathVariable receiverId: Long): ResponseEntity<List<TransactionResponseDTO>> {
        return ResponseEntity.ok(transactionService.getTransactionsByReceiver(receiverId))
    }
}