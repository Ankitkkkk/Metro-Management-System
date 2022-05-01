package com.mms.infrastructure.serialization

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.mms.infrastructure.exception.InvalidApiKeyException
import com.mms.infrastructure.exception.InvalidJsonException
import com.mms.mms.Constants


class JsonApiHelper(var command: JsonObject = JsonObject(),
                    var rawCommand: String = "{}",
                    var authority: String = "",
                    var token: String = ""
                    ) {
    private val gson: Gson = Gson()
    constructor(command: JsonObject):this() {
        this.command = command
    }

    constructor(rawCommand: String): this() {
        this.rawCommand = rawCommand
    }

//    constructor() : this(JsonObject(),"{}","") {}

    fun jsonCommandGenerator() : JsonApiHelper {
        this.command = gson.fromJson(rawCommand,JsonObject::class.java)
        return this
    }

    fun validateJsonString(): JsonApiHelper {
        try {
            JsonParser.parseString(rawCommand)
        } catch (e: JsonSyntaxException) {
            throw InvalidJsonException(e.message)
        }
        return this
    }
    fun setAuthorityForCommand(key : String): JsonApiHelper {
        when(key) {
            Constants.SUPER_ADMIN_KEY -> this.authority = "SUPER"
            Constants.ADMIN_KEY -> this.authority = "ADMIN"
            Constants.USER_KEY -> this.authority = "USER"
            else -> throw InvalidApiKeyException(key)
        }
        return this
    }
    fun setToken(token : String): JsonApiHelper {
        this.token = token
        return this
    }
}