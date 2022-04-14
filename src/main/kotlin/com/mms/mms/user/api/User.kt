package com.mms.mms.user.api

import com.mms.infrastructure.serialization.JsonApiHelper
import com.mms.mms.user.dao.UserRepository
import com.mms.mms.user.domain.User
import com.mms.mms.user.service.UserValidator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
class User(private val userRepository: UserRepository,private val userValidator: UserValidator) {
    var helper: JsonApiHelper? = null

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(this.userRepository.findAll())
    }
 
    @PostMapping
    fun addUser(@RequestBody user: String) : String {
        helper = JsonApiHelper(user)
        helper!!.jsonCommandGenerator()
        val validationError = userValidator.validateForCreate(helper!!.command)
//        if(validationError != null) {
//            return validationError
//        }
        return ""
    //        println(user.name)
//        var savedUser : User = this.userRepository!!.save(user)
//        return savedUser.id.toString()
    }

    // json testing
    @PostMapping("/test")
    fun testing(@RequestBody command: String) : String {
        helper = JsonApiHelper(command)
        helper!!.jsonCommandGenerator()
        return ""
    }

}