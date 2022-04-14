package com.mms.mms.user.service

import com.google.gson.JsonObject
import com.mms.infrastructure.exception.ExceptionInfo
import com.mms.infrastructure.exception.ValidationException
import com.mms.mms.Constants
import org.springframework.stereotype.Service

@Service
class UserValidator {
    var command: JsonObject? = null
    var exceptionInfo = ExceptionInfo()
    val allowedAttributes: Set<String> = setOf("name","age","amount")

    public fun validateForCreate(command: JsonObject?) {
        this.command = command
        exceptionInfo = ExceptionInfo()
        validateForAllowed()
        validateForMissing()

    }
    fun validateForAllowed() {
        val keys: Set<String> = command!!.keySet()
        // Exception info updated
        exceptionInfo.reason = Constants.BAD_PARAM

        for(attribute in keys) {
            if(!allowedAttributes.contains(attribute)) {
                val exceptionTOBeAdded = JsonObject()
                exceptionTOBeAdded.add(attribute,command!!.get(attribute))
                exceptionInfo.addExceptionToList(exceptionTOBeAdded)
            }
        }
        if(exceptionInfo.sizeOfListOfExceptions() != 0) {
            throw ValidationException(exceptionInfo)
        }
    }
    fun validateForMissing() {
        exceptionInfo.reason = Constants.MISSING_PARAM

        val keys: MutableSet<String> = command!!.keySet()
        val shouldBePresent : MutableSet<String> = allowedAttributes.toMutableSet()
        for(attributes in keys) {
            shouldBePresent.remove(attributes)
        }

        for (attribute in shouldBePresent) {
            val exceptionTOBeAdded = JsonObject()
            exceptionTOBeAdded.add(attribute,command!!.get(attribute))
            exceptionInfo.addExceptionToList(exceptionTOBeAdded)
        }

        if (exceptionInfo.sizeOfListOfExceptions() != 0) throw ValidationException(exceptionInfo)
    }

}