package com.mms.mms.user.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="mms_user_login_credentials")
class UserSql(@Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long?,
              var email: String = "",
              var password: String = "",
              var mongoId: String = "",
              var token: String = "",
              var role: String = ""
              ) {
    constructor(): this (null) {}

    constructor(email: String,password: String,mongoId: String) : this(null) {
        this.email = email
        this.password = password
        this.mongoId = mongoId
    }
}