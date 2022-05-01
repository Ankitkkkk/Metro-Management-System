package com.mms.mms.user.service

import com.mms.infrastructure.exception.EmailAlreadyExistsException
import com.mms.infrastructure.serialization.CommandExtractor
import com.mms.infrastructure.serialization.JsonApiHelper
import com.mms.mms.user.dao.UserRepositoryMongo
import com.mms.mms.user.dao.UserRepositorySql
import com.mms.mms.user.domain.UserMongo
import com.mms.mms.user.domain.UserSql
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserWriteService(private val userRepositoryMongo: UserRepositoryMongo,
                       private val userRepositorySql: UserRepositorySql,
                       private val userValidator: UserValidator
                       ) {

    fun addUser(jsonApiHelper: JsonApiHelper): String {

        val user: UserMongo = userValidator.createAndValidateUserDataFromJson(jsonApiHelper.command)


        isEmailAlreadyExists(user.email)
        println(user.toString())
        user.createdOn = Date(System.currentTimeMillis())
        user.modifiedOn = user.createdOn
        var savedUserMongo: UserMongo = userRepositoryMongo.save(user)

        val commandExtractor = CommandExtractor(jsonApiHelper.command)

        println("id of saved user is ${savedUserMongo.id} and name is ${savedUserMongo.name}")

        val sqlUser = UserSql(savedUserMongo.email,
            commandExtractor.extractStringParameterNamed("password"),
            savedUserMongo.id!!)

        sqlUser.role = jsonApiHelper.authority
        val savedUserSql: UserSql = userRepositorySql.save(sqlUser)
        savedUserMongo.sqlId = savedUserSql.id
        savedUserMongo = userRepositoryMongo.save(savedUserMongo)
        return "{\"sqlResourceId\":\"${savedUserSql.id}\",\"mongoResourceId\":\"${savedUserMongo.id}\"}"
    }

    private fun isEmailAlreadyExists(email: String) {
        if(userRepositorySql.existsByEmail(email)) {
            throw EmailAlreadyExistsException(email)
        }
    }
}