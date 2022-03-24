package com.mms.mms.user.api

import com.mms.mms.user.dao.UserRepository
import com.mms.mms.user.domain.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class User(private val userRepository: UserRepository) {


    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserRepository>> {
        return ResponseEntity.ok(this.userRepository.findAll())
    }

    @PostMapping
    fun addUser(@RequestBody user: User) : String {
        println(user.name)
        var savedUser : User = this.userRepository!!.save(User(user.name))
        return savedUser.id
    }
}