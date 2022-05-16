package com.mms.mms.user.dao

import com.mms.mms.user.domain.UserMongo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepositoryMongo : MongoRepository<UserMongo, String> {


}