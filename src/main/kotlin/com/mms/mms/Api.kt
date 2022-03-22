package com.mms.mms

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class Api {
    @GetMapping
    fun health() : String {
        return "200 ok"
    }
}


