package com.mms.mms.user.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
class User {
    @GetMapping
    fun getAllUsers(): String {
        return "['user1','user2']"
    }
}