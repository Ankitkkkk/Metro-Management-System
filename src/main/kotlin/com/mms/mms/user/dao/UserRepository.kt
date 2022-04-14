package com.mms.mms.user.dao

import org.springframework.data.mongodb.repository.MongoRepository
import com.mms.mms.user.domain.User

interface UserRepository : MongoRepository<User, String> {
}