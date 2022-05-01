package com.mms.mms.user.dao

import com.mms.mms.user.domain.UserSql
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepositorySql : CrudRepository<UserSql, Long> {
    fun existsByEmail(email: String? = ""): Boolean
    fun findByEmail(email: String? = ""): UserSql?
    fun findByToken(token: String? = ""): UserSql?
}