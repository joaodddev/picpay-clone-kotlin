package br.com.joaodddev.picpayclone.infrastructure.user

import br.com.joaodddev.picpayclone.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
    fun findByDocument(document: String): Optional<User>
    fun existsByEmail(email: String): Boolean
    fun existsByDocument(document: String): Boolean
}