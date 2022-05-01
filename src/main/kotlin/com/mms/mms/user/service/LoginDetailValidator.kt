package com.mms.mms.user.service

import com.google.gson.JsonObject
import com.mms.infrastructure.validators.JsonValidators
import org.springframework.stereotype.Service

@Service
class LoginDetailValidator {

    private val allowedAttributes: Set<String> = setOf("email","password")
    fun validateForLogin(command : JsonObject) {
        val jsonValidators = JsonValidators(command,allowedAttributes)
        jsonValidators.validateForMissing()
        jsonValidators.validateForRequired()
    }
}