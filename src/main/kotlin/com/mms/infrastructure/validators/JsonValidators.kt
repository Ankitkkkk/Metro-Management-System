package com.mms.infrastructure.validators

import com.google.gson.JsonObject
import com.mms.infrastructure.exception.ExceptionInfo
import com.mms.infrastructure.exception.ValidationException
import com.mms.mms.Constants

class JsonValidators(var command: JsonObject,
                     var allowedAttributes: Set<String>) {
    var exceptionInfo = ExceptionInfo()

    fun validateForRequired() {
        val keys: Set<String> = command.keySet()
        // Exception info updated
        exceptionInfo.reason = Constants.BAD_PARAM

        for(attribute in keys) {
            if(!allowedAttributes.contains(attribute)) {
                val exceptionTOBeAdded = JsonObject()
                exceptionTOBeAdded.add(attribute,command.get(attribute))
                exceptionInfo.addExceptionToList(exceptionTOBeAdded)
            }
        }
        if(exceptionInfo.sizeOfListOfExceptions() != 0) {
            throw ValidationException(exceptionInfo)
        }
    }

    fun validateForMissing() {
        exceptionInfo.reason = Constants.MISSING_PARAM

        val keys: MutableSet<String> = command.keySet()
        val shouldBePresent : MutableSet<String> = allowedAttributes.toMutableSet()
        for(attributes in keys) {
            shouldBePresent.remove(attributes)
        }

        for (attribute in shouldBePresent) {
            val exceptionTOBeAdded = JsonObject()
            exceptionTOBeAdded.add(attribute,command.get(attribute))
            exceptionInfo.addExceptionToList(exceptionTOBeAdded)
        }

        if (exceptionInfo.sizeOfListOfExceptions() != 0) throw ValidationException(exceptionInfo)
    }


}