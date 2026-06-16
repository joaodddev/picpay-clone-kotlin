package br.com.joaodddev.picpayclone.infrastructure.user

import br.com.joaodddev.picpayclone.domain.user.User
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
    fun findByDocument(document: String): Optional<User>
    fun existsByEmail(email: String): Boolean
    fun existsByDocument(document: String): Boolean

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.id = :id")
    fun findByIdWithLock(id: Long): Optional<User>
}