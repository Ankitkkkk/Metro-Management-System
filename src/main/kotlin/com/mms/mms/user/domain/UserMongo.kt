package com.mms.mms.user.domain


import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "mms_user_basic_credentials")
class
UserMongo(
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: String?,
    // basic details
    var name: String,

    // login details
    var email: String,

    // other details
    var createdOn: Date?,
    var modifiedOn: Date?,
    var fareDetails: MutableList<FareDetail>,

    // mysql mapping
    var sqlId: Long?,

    // balance
    var balance: Int
) {
    constructor(name: String, email: String) : this(null,name,email,null,null,ArrayList(),null,0) {
        this.name = name
        this.email = email
    }

    fun mapSqlWithMongo(user: UserMongo, sqlId: Long) {
        user.sqlId = sqlId;
    }

}
