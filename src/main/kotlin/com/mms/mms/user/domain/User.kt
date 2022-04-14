package com.mms.mms.user.domain


import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.security.Timestamp

@Document(collection = "user")
class User(
    @Id
    val id: ObjectId? = ObjectId.get(),
    // basic details
    val name: String?,
    val dob: Timestamp,

    // login details
    val email: String,
    val password: String,

    // other details
    val createdOn: Timestamp,
    val modifiedOn: Timestamp,
    val fareDetails: MutableList<FareDetail>,

    // balance
    val balance: Int
)

class FareDetail(
    val from: String,
    val to: String,
    val checkInTime: Timestamp,
    val checkOutTime: Timestamp,
    val totalFare: Int
)