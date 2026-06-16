package br.com.joaodddev.picpayclone.infrastructure.security

import br.com.joaodddev.picpayclone.domain.user.User
import br.com.joaodddev.picpayclone.domain.user.UserType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils
import java.math.BigDecimal

class JwtServiceTest {

    private lateinit var jwtService: JwtService

    private val testUser = User(
        id = 1L,
        fullName = "João Dev",
        document = "12345678901",
        email = "joao@email.com",
        password = "encoded",
        balance = BigDecimal("500.00"),
        userType = UserType.COMMON
    )

    @BeforeEach
    fun setup() {
        jwtService = JwtService()
        ReflectionTestUtils.setField(
            jwtService,
            "secret",
            "3f8a2b7c1d9e4f6a0b5c8d2e7f1a4b9c3d6e0f2a5b8c1d4e7f0a3b6c9d2e5f8"
        )
        ReflectionTestUtils.setField(jwtService, "expiration", 86400000L)
    }

    @Test
    fun `should generate token`() {
        val token = jwtService.generateToken(testUser.email)
        assertNotNull(token)
        assertTrue(token.isNotBlank())
    }

    @Test
    fun `should extract email from token`() {
        val token = jwtService.generateToken(testUser.email)
        val extracted = jwtService.extractEmail(token)
        assertEquals(testUser.email, extracted)
    }

    @Test
    fun `should validate token successfully`() {
        val token = jwtService.generateToken(testUser.email)
        val isValid = jwtService.isTokenValid(token, testUser)
        assertTrue(isValid)
    }

    @Test
    fun `should reject token for different user`() {
        val token = jwtService.generateToken("outro@email.com")
        val isValid = jwtService.isTokenValid(token, testUser)
        assertFalse(isValid)
    }
}