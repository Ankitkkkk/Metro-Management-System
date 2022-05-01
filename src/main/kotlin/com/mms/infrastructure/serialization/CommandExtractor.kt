package com.mms.infrastructure.serialization

import com.google.gson.JsonObject



class CommandExtractor(var command: JsonObject) {
    fun extractStringParameterNamed(param: String) : String {
        return this.command.get(param).asString
    }
}