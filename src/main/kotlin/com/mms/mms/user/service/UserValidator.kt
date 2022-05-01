package com.mms.mms.user.service

import com.google.gson.JsonObject
import com.mms.infrastructure.exception.ExceptionInfo
import com.mms.infrastructure.serialization.CommandExtractor
import com.mms.infrastructure.validators.JsonValidators
import com.mms.mms.user.domain.UserMongo
import org.springframework.stereotype.Service

@Service
class UserValidator {
    var command: JsonObject? = null
    var exceptionInfo = ExceptionInfo()
    val allowedAttributes: Set<String> = setOf("name","email","password")

    fun createAndValidateUserDataFromJson(command: JsonObject) : UserMongo {
        this
            .setCommand(command)
            .validateForCreate()
        val commandExtractor = CommandExtractor(command)
        val name = commandExtractor.extractStringParameterNamed("name")
        val email = commandExtractor.extractStringParameterNamed("email")
        return UserMongo(name,email)
    }

    fun validateForCreate() {
        val jsonValidators = JsonValidators(this.command!!,allowedAttributes)
        jsonValidators.validateForMissing()
        jsonValidators.validateForRequired()
    }
    fun setCommand(command: JsonObject): UserValidator {
        this.command = command
        return this
    }
}