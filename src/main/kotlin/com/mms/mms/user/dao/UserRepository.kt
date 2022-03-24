package com.mms.mms.user.dao

import org.springframework.data.mongodb.repository.MongoRepository
import com.mms.mms.user.domain.User
// fun save(user : User): User
// fun save(user : User): User

interface UserRepository : MongoRepository<UserRepository, String> {
}