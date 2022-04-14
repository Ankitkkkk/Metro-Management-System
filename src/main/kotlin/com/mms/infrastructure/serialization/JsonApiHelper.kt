package com.mms.infrastructure.serialization

import com.google.gson.Gson
import com.google.gson.JsonObject

class JsonApiHelper(var command: JsonObject?,
                    var rawCommand: String?,
                    var resourceId: String?,
                    ) {
    private val gson: Gson = Gson()
    constructor(command: JsonObject): this(command,null,null) {
        this.command = command
    }

    constructor(rawCommand: String): this(null,rawCommand,null) {
        this.rawCommand = rawCommand
    }

    constructor() : this(null,null,null) {}

    fun jsonCommandGenerator() {
        if(this.rawCommand != null) {
            this.command = gson.fromJson(rawCommand,JsonObject::class.java)
        }
    }

}