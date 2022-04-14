package com.mms.infrastructure.exception

import com.google.gson.JsonObject


class ExceptionInfo(var reason: String?, var listOfException: MutableList<JsonObject>) {
    /*
    * @param listOfException parameter
    * MutableList:
    *               JsonObject
    */
    constructor() : this(null,ArrayList()) {
        this.reason = null
        this.listOfException = ArrayList()
    }
    constructor(reason: String): this(reason,ArrayList()) {
        this.reason = reason
        this.listOfException = ArrayList()
    }

    fun addExceptionToList(exception: JsonObject) {
        this.listOfException.add(exception)
    }

    fun sizeOfListOfExceptions() : Int {
        return listOfException.size
    }

}