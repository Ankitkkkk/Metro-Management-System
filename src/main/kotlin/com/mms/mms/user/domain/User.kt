package com.mms.mms.user.domain


import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id

@Document(collection = "user")
class User {
    @Id
    var id : String = ""
    var name: String = ""

    constructor(name: String) {
        this.name = name
    }
}