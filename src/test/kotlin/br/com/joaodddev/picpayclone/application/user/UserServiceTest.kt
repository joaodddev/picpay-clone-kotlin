package br.com.joaodddev.picpayclone.application.user

import br.com.joaodddev.picpayclone.domain.user.User
import br.com.joaodddev.picpayclone.domain.user.UserType
import br.com.joaodddev.picpayclone.infrastructure.user.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.math.BigDecimal
import java.util.Optional

class UserServiceTest {

    private val userRepository: UserRepository = mock()
    private val passwordEncoder = BCryptPasswordEncoder()
    private lateinit var userService: UserService

    private val defaultUser = User(
        id = 1L,
        fullName = "João Dev",
        document = "12345678901",
        email = "joao@email.com",
        password = passwordEncoder.encode("123456"),
        balance = BigDecimal("500.00"),
        userType = UserType.COMMON
    )

    private val defaultRequest = UserRequestDTO(
        fullName = "João Dev",
        document = "12345678901",
        email = "joao@email.com",
        password = "123456",
        balance = BigDecimal("500.00"),
        userType = UserType.COMMON
    )

    @BeforeEach
    fun setup() {
        userService = UserService(userRepository, passwordEncoder)
    }

    @Test
    fun `should create user successfully`() {
        whenever(userRepository.existsByEmail(defaultRequest.email)).thenReturn(false)
        whenever(userRepository.existsByDocument(defaultRequest.document)).thenReturn(false)
        whenever(userRepository.save(any())).thenReturn(defaultUser)

        val result = userService.createUser(defaultRequest)

        assertEquals(defaultUser.id, result.id)
        assertEquals(defaultUser.email, result.email)
        assertEquals(defaultUser.fullName, result.fullName)
        verify(userRepository, times(1)).save(any())
    }

    @Test
    fun `should throw when email already in use`() {
        whenever(userRepository.existsByEmail(defaultRequest.email)).thenReturn(true)

        val exception = assertThrows<IllegalArgumentException> {
            userService.createUser(defaultRequest)
        }

        assertEquals("Email already in use", exception.message)
        verify(userRepository, never()).save(any())
    }

    @Test
    fun `should throw when document already in use`() {
        whenever(userRepository.existsByEmail(defaultRequest.email)).thenReturn(false)
        whenever(userRepository.existsByDocument(defaultRequest.document)).thenReturn(true)

        val exception = assertThrows<IllegalArgumentException> {
            userService.createUser(defaultRequest)
        }

        assertEquals("Document already in use", exception.message)
        verify(userRepository, never()).save(any())
    }

    @Test
    fun `should return all users`() {
        whenever(userRepository.findAll()).thenReturn(listOf(defaultUser))

        val result = userService.getAllUsers()

        assertEquals(1, result.size)
        assertEquals(defaultUser.email, result[0].email)
    }

    @Test
    fun `should return user by id`() {
        whenever(userRepository.findById(1L)).thenReturn(Optional.of(defaultUser))

        val result = userService.getUserById(1L)

        assertEquals(defaultUser.id, result.id)
        assertEquals(defaultUser.email, result.email)
    }

    @Test
    fun `should throw when user not found by id`() {
        whenever(userRepository.findById(99L)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            userService.getUserById(99L)
        }

        assertTrue(exception.message!!.contains("99"))
    }

    @Test
    fun `should delete user successfully`() {
        whenever(userRepository.existsById(1L)).thenReturn(true)

        userService.deleteUser(1L)

        verify(userRepository, times(1)).deleteById(1L)
    }

    @Test
    fun `should throw when deleting non-existent user`() {
        whenever(userRepository.existsById(99L)).thenReturn(false)

        assertThrows<NoSuchElementException> {
            userService.deleteUser(99L)
        }

        verify(userRepository, never()).deleteById(any())
    }
}