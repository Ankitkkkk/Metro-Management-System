package com.mms.mms.user.api

import com.google.gson.JsonObject
import com.mms.infrastructure.serialization.JsonApiHelper
import com.mms.mms.user.dao.UserRepositoryMongo
import com.mms.mms.user.service.LoginDetailValidator
import com.mms.mms.user.service.UserReadService
import com.mms.mms.user.service.UserValidator
import com.mms.mms.user.service.UserWriteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin
@RequestMapping("/user")
class UserApi(private val userWriteService: UserWriteService,
              private val userValidator: UserValidator,
              private val loginDetailValidator: LoginDetailValidator,
              private val userReadService: UserReadService,
              private val userRepositoryMongo: UserRepositoryMongo
           ) {




    var helper: JsonApiHelper? = null

    // for signup
    @PostMapping("/signup")
    fun userSignUp(@RequestBody user: String, @RequestHeader("api-key") key: String?) : ResponseEntity<String> {
        var apiKey: String? = key
        if(apiKey == null) {
            apiKey = "no.key.supplied"
        }
        println("apiKey : $apiKey")

        helper = JsonApiHelper(user)
            .setAuthorityForCommand(apiKey)
            .validateJsonString()
            .jsonCommandGenerator()

        val command: JsonObject = helper!!.command
        return ResponseEntity.status(HttpStatus.CREATED).body(userWriteService.addUser(helper!!))
    }
    // for login and token generation
    @GetMapping("/login")
    fun userLogin(@RequestBody user: String, @RequestHeader("api-key") key: String?) : ResponseEntity<String> {
        var apiKey: String? = key
        if(apiKey == null) {
            apiKey = "no.key.supplied"
        }
        helper = JsonApiHelper(user)
            .setAuthorityForCommand(apiKey)
            .validateJsonString()
            .jsonCommandGenerator()

        val command: JsonObject = helper!!.command
        this.loginDetailValidator.validateForLogin(command)
        return ResponseEntity.ok(userReadService.generateToken(command))
    }



//    @GetMapping
//    fun getUsers(@RequestHeader("api-key") key: String?,
//                    @RequestParam param: Map<String,String>,
//                    @RequestHeader("Authorization") authorization: String = "Bearer "
//                    ): ResponseEntity<List<UserMongo>> {
//        var apiKey: String? = key
//        if(apiKey == null) {
//            apiKey = "no.key.supplied"
//        }
//        val helper = JsonApiHelper()
//            .setToken(apiKey)
//        val token = authorization.substring(7)
//        val command : JsonObject = createCommandForGetUser(token,param)
//        return ResponseEntity.ok()
//    }
 
//    @PostMapping
//    fun addUser(@RequestBody user: String) : String {
//        helper = JsonApiHelper(user)
//        helper!!.validateJsonString()
//        val command: JsonObject = helper!!.command!!
//        val validationError = userValidator.validateForCreate(command)
////        if(validationError != null) {
////            return validationError
////        }
//        return "{'res':'ok'}"
//    //        println(user.name)
////        var savedUser : User = this.userRepository!!.save(user)
////        return savedUser.id.toString()
//    }

    // json testing
//    @PostMapping("/test")
//    fun testing(@RequestBody command: String) : String {
//        helper = JsonApiHelper(command)
//        helper!!.jsonCommandGenerator()
//        return ""
//    }
    fun createCommandForGetUser(token: String,param: Map<String, String>) : JsonObject {
        val json = JsonObject()
        json.addProperty("token",token)
        val jsonParam = JsonObject()
        param.forEach { (key, value) -> jsonParam.addProperty(key,value)  }
        json.add("params",jsonParam)
        return  json
    }
}