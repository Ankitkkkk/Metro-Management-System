package com.mms.mms.security.service

import com.mms.mms.user.dao.UserRepositorySql
import com.mms.mms.user.domain.UserSql
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserAuthService(private val userRepositorySql: UserRepositorySql) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user: UserSql? = this.userRepositorySql.findByEmail(username)
        if(user != null) {
//            val user: UserSql = this.userRepositorySql.findByEmail(username)
            return User(user.email,user.password,ArrayList())
        }
        else throw UsernameNotFoundException("$username is not present")
    }
}