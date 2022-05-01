package com.mms.mms.security.api

import com.google.gson.JsonObject
import com.mms.infrastructure.serialization.JsonApiHelper
import com.mms.mms.user.service.LoginDetailValidator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class Login(private val loginDetailValidator: LoginDetailValidator) {
    var helper = JsonApiHelper()
    @GetMapping
    fun generateToken(@RequestBody user: String) : ResponseEntity<Any> {
        helper.rawCommand = user
        helper.validateJsonString()
        helper.jsonCommandGenerator()
        val command: JsonObject = helper.command!!
        loginDetailValidator.validateForLogin(command)
        return ResponseEntity.status(HttpStatus.OK).body(command)
    }
}