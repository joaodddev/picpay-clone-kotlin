package br.com.joaodddev.picpayclone.application.transaction

import br.com.joaodddev.picpayclone.domain.transaction.Transaction
import br.com.joaodddev.picpayclone.domain.user.User
import br.com.joaodddev.picpayclone.domain.user.UserType
import br.com.joaodddev.picpayclone.infrastructure.transaction.TransactionRepository
import br.com.joaodddev.picpayclone.infrastructure.user.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.Optional

class TransactionServiceTest {

    private val transactionRepository: TransactionRepository = mock()
    private val userRepository: UserRepository = mock()
    private lateinit var transactionService: TransactionService

    private val sender = User(
        id = 1L,
        fullName = "João Common",
        document = "11111111111",
        email = "common@email.com",
        password = "encoded",
        balance = BigDecimal("500.00"),
        userType = UserType.COMMON
    )

    private val receiver = User(
        id = 2L,
        fullName = "Loja Merchant",
        document = "22222222222",
        email = "merchant@email.com",
        password = "encoded",
        balance = BigDecimal("0.00"),
        userType = UserType.MERCHANT
    )

    private val defaultRequest = TransactionRequestDTO(
        senderId = 1L,
        receiverId = 2L,
        amount = BigDecimal("100.00")
    )

    @BeforeEach
    fun setup() {
        transactionService = TransactionService(transactionRepository, userRepository)
    }

    @Test
    fun `should transfer successfully`() {
        whenever(userRepository.findByIdWithLock(1L)).thenReturn(Optional.of(sender))
        whenever(userRepository.findByIdWithLock(2L)).thenReturn(Optional.of(receiver))
        whenever(userRepository.save(any())).thenAnswer { it.arguments[0] as User }
        whenever(transactionRepository.save(any())).thenAnswer {
            val t = it.arguments[0] as Transaction
            t.copy(id = 1L)
        }

        val result = transactionService.transfer(defaultRequest)

        assertEquals(BigDecimal("100.00"), result.amount)
        assertEquals(1L, result.senderId)
        assertEquals(2L, result.receiverId)
        verify(userRepository, times(2)).save(any())
        verify(transactionRepository, times(1)).save(any())
    }

    @Test
    fun `should throw when sender is merchant`() {
        val merchantSender = sender.copy(userType = UserType.MERCHANT)
        whenever(userRepository.findByIdWithLock(1L)).thenReturn(Optional.of(merchantSender))
        whenever(userRepository.findByIdWithLock(2L)).thenReturn(Optional.of(receiver))

        val exception = assertThrows<IllegalArgumentException> {
            transactionService.transfer(defaultRequest)
        }

        assertEquals("Merchants cannot send transfers", exception.message)
        verify(transactionRepository, never()).save(any())
    }

    @Test
    fun `should throw when sender has insufficient balance`() {
        val brokeRequest = defaultRequest.copy(amount = BigDecimal("1000.00"))
        whenever(userRepository.findByIdWithLock(1L)).thenReturn(Optional.of(sender))
        whenever(userRepository.findByIdWithLock(2L)).thenReturn(Optional.of(receiver))

        val exception = assertThrows<IllegalArgumentException> {
            transactionService.transfer(brokeRequest)
        }

        assertEquals("Insufficient balance", exception.message)
        verify(transactionRepository, never()).save(any())
    }

    @Test
    fun `should throw when sender and receiver are the same`() {
        val sameUserRequest = defaultRequest.copy(receiverId = 1L)

        val exception = assertThrows<IllegalArgumentException> {
            transactionService.transfer(sameUserRequest)
        }

        assertEquals("Sender and receiver must be different users", exception.message)
        verify(userRepository, never()).findByIdWithLock(any())
    }

    @Test
    fun `should throw when sender not found`() {
        whenever(userRepository.findByIdWithLock(1L)).thenReturn(Optional.empty())
        whenever(userRepository.findByIdWithLock(2L)).thenReturn(Optional.of(receiver))

        assertThrows<NoSuchElementException> {
            transactionService.transfer(defaultRequest)
        }
    }

    @Test
    fun `should return all transactions`() {
        val transaction = Transaction(
            id = 1L,
            sender = sender,
            receiver = receiver,
            amount = BigDecimal("100.00"),
            createdAt = LocalDateTime.now()
        )
        whenever(transactionRepository.findAll()).thenReturn(listOf(transaction))

        val result = transactionService.getAllTransactions()

        assertEquals(1, result.size)
        assertEquals(BigDecimal("100.00"), result[0].amount)
    }
}