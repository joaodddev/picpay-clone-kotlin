package br.com.joaodddev.picpayclone.infrastructure.transaction

import br.com.joaodddev.picpayclone.domain.transaction.Transaction
import br.com.joaodddev.picpayclone.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findAllBySender(sender: User): List<Transaction>
    fun findAllByReceiver(receiver: User): List<Transaction>
}