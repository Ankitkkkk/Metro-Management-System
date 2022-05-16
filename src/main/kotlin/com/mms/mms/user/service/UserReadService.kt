package com.mms.mms.user.service

import com.google.gson.JsonObject
import com.mms.infrastructure.exception.BadLoginCredentialException
import com.mms.infrastructure.serialization.CommandExtractor
import com.mms.infrastructure.serialization.JsonApiHelper
import com.mms.mms.Constants
import com.mms.mms.security.service.UserAuthService
import com.mms.mms.security.token.JwtUtil
import com.mms.mms.user.dao.UserRepositoryMongo
import com.mms.mms.user.dao.UserRepositorySql
import com.mms.mms.user.domain.UserMongo
import com.mms.mms.user.domain.UserSql
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserReadService(private val userAuthService: UserAuthService,
                      private val jwtUtil: JwtUtil,
                      private val authenticationManager: AuthenticationManager,
                      private val userRepositorySql: UserRepositorySql,
                      private val userRepositoryMongo: UserRepositoryMongo
                      ) {
    private val commandExtractor: CommandExtractor = CommandExtractor(JsonObject())
    fun generateToken(command: JsonObject) : String {
        this.commandExtractor.command = command

        val username: String = this.commandExtractor.extractStringParameterNamed("email")
        val password: String = this.commandExtractor.extractStringParameterNamed("password")
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username,password))

        } catch (e: BadCredentialsException) {
            throw BadLoginCredentialException()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val userDetails: UserDetails = this.userAuthService.loadUserByUsername(username)
        val token: String = this.jwtUtil.generateToken(userDetails)
        val user = this.userRepositorySql.findByEmail(username)
        user!!.token = token
        this.userRepositorySql.save(user)
        return "{\"token\":\"$token\"}"
    }

    // for single user self detail
    fun getUserDetails(command: JsonObject) : String {
        val userSql : UserSql? = this.userRepositorySql.findByToken(command.get("token").toString())

        var commandDetail: Boolean = false
        if(command["params"].asJsonObject.get("allUsers") != null) {
            commandDetail = command["params"].asJsonObject.get("allUsers").toString().toBoolean()
        }

        if (!commandDetail) {
            val user = userRepositorySql.findByToken()
        }
        return ""
    }

    fun getUserDataFromMongoDB(helper: JsonApiHelper) : MutableList<UserMongo> {
        val user : UserSql? = userRepositorySql.findByToken(helper.token)
        println(helper.token)
        if (helper.authority == Constants.USER_KEY){
            return ArrayList()
        }
        if (user !=null &&user.role == "USER"){
            return ArrayList()
        }
        return userRepositoryMongo.findAll()
    }
}
