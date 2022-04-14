package com.mms.mms.exceptionHandlers

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mms.infrastructure.exception.ValidationException
import com.mms.mms.Constants
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(ValidationException::class)
    fun validationExceptionHandler(validationException: ValidationException): ResponseEntity<String> {
        when(validationException.exception.reason) {
            Constants.BAD_PARAM -> return validationExceptionHandlerForExtraAttributes(validationException)
            Constants.MISSING_PARAM -> return validationExceptionHandlerForMissingAttributes(validationException)
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ " +
                "\"reason\"" + ":" + "\"this.validation.is.not.mapped\"" +
                "}")
    }



    fun validationExceptionHandlerForExtraAttributes(validationException: ValidationException): ResponseEntity<String> {
         val responseObject = JsonObject()
        //adding error code
        responseObject.addProperty("ErrorCode",400)
        responseObject.addProperty("RootError",validationException.exception.reason)
        responseObject.addProperty("dev-message","this.response.body.is.not.good")
        val badAttributes = JsonArray()

        validationException.exception.listOfException.forEach { element -> run {
            val keySet: String = element.keySet().first()
            val errorElement = JsonObject()
            errorElement.addProperty("user-message","this.$keySet.is.not.allowed")
            val detail = JsonObject()
            detail.add("value",element.get(keySet))
            errorElement.add(keySet,detail)
            badAttributes.add(errorElement)
        } }
        responseObject.add("reason",badAttributes)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject.toString())
    }

    fun validationExceptionHandlerForMissingAttributes(validationException: ValidationException): ResponseEntity<String> {
        val responseObject = JsonObject()
        //adding error code
        responseObject.addProperty("ErrorCode",400)
        responseObject.addProperty("RootError",validationException.exception.reason)
        responseObject.addProperty("dev-message","this.response.body.is.missing.important.attributes")
        val missingAttributes = JsonArray()

        validationException.exception.listOfException.forEach { element -> run {
            val keySet: String = element.keySet().first()
            val errorElement = JsonObject()
            errorElement.addProperty("user-message","this.$keySet.is.important.and.is.missing")
            val detail = JsonObject()
            detail.add("value",element.get(keySet))
            errorElement.add(keySet,detail)
            missingAttributes.add(errorElement)
        } }
        responseObject.add("reason",missingAttributes)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject.toString())
    }

}