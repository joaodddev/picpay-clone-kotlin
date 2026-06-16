package br.com.joaodddev.picpayclone.application.transaction

import br.com.joaodddev.picpayclone.domain.transaction.Transaction
import br.com.joaodddev.picpayclone.domain.user.UserType
import br.com.joaodddev.picpayclone.infrastructure.transaction.TransactionRepository
import br.com.joaodddev.picpayclone.infrastructure.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun transfer(request: TransactionRequestDTO): TransactionResponseDTO {

        if (request.senderId == request.receiverId) {
            throw IllegalArgumentException("Sender and receiver must be different users")
        }

        val sender = userRepository.findByIdWithLock(request.senderId)
            .orElseThrow { NoSuchElementException("Sender not found with id: ${request.senderId}") }

        val receiver = userRepository.findByIdWithLock(request.receiverId)
            .orElseThrow { NoSuchElementException("Receiver not found with id: ${request.receiverId}") }

        if (sender.userType == UserType.MERCHANT) {
            throw IllegalArgumentException("Merchants cannot send transfers")
        }

        if (sender.balance < request.amount) {
            throw IllegalArgumentException("Insufficient balance")
        }

        val updatedSender = sender.copy(balance = sender.balance - request.amount)
        val updatedReceiver = receiver.copy(balance = receiver.balance + request.amount)

        userRepository.save(updatedSender)
        userRepository.save(updatedReceiver)

        val transaction = Transaction(
            sender = updatedSender,
            receiver = updatedReceiver,
            amount = request.amount
        )

        val saved = transactionRepository.save(transaction)
        return TransactionResponseDTO.fromEntity(saved)
    }

    fun getTransactionById(id: Long): TransactionResponseDTO {
        val transaction = transactionRepository.findById(id)
            .orElseThrow { NoSuchElementException("Transaction not found with id: $id") }
        return TransactionResponseDTO.fromEntity(transaction)
    }

    fun getAllTransactions(): List<TransactionResponseDTO> {
        return transactionRepository.findAll().map { TransactionResponseDTO.fromEntity(it) }
    }

    fun getTransactionsBySender(senderId: Long): List<TransactionResponseDTO> {
        val sender = userRepository.findById(senderId)
            .orElseThrow { NoSuchElementException("User not found with id: $senderId") }
        return transactionRepository.findAllBySender(sender)
            .map { TransactionResponseDTO.fromEntity(it) }
    }

    fun getTransactionsByReceiver(receiverId: Long): List<TransactionResponseDTO> {
        val receiver = userRepository.findById(receiverId)
            .orElseThrow { NoSuchElementException("User not found with id: $receiverId") }
        return transactionRepository.findAllByReceiver(receiver)
            .map { TransactionResponseDTO.fromEntity(it) }
    }
}