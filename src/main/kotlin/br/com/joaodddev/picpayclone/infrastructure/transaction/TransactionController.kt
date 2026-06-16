package br.com.joaodddev.picpayclone.infrastructure.transaction

import br.com.joaodddev.picpayclone.application.transaction.TransactionRequestDTO
import br.com.joaodddev.picpayclone.application.transaction.TransactionResponseDTO
import br.com.joaodddev.picpayclone.application.transaction.TransactionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transactions")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping
    fun transfer(@Valid @RequestBody request: TransactionRequestDTO): ResponseEntity<TransactionResponseDTO> {
        val response = transactionService.transfer(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun getAllTransactions(): ResponseEntity<List<TransactionResponseDTO>> {
        return ResponseEntity.ok(transactionService.getAllTransactions())
    }

    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable id: Long): ResponseEntity<TransactionResponseDTO> {
        return ResponseEntity.ok(transactionService.getTransactionById(id))
    }

    @GetMapping("/sender/{senderId}")
    fun getBySender(@PathVariable senderId: Long): ResponseEntity<List<TransactionResponseDTO>> {
        return ResponseEntity.ok(transactionService.getTransactionsBySender(senderId))
    }

    @GetMapping("/receiver/{receiverId}")
    fun getByReceiver(@PathVariable receiverId: Long): ResponseEntity<List<TransactionResponseDTO>> {
        return ResponseEntity.ok(transactionService.getTransactionsByReceiver(receiverId))
    }
}