package com.kotlinspring.exceptionhandler

import com.kotlinspring.exception.InstructorNotValidException
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@Component // This needs to be scanned as bean
@ControllerAdvice // This annotation will be acting  as a proxy to track any kind of exception thats drawn from code logic that part of the controller.
class GlobalErrorHandler : ResponseEntityExceptionHandler() { // This is one of the class which takes up handling exception by default

    companion object : KLogging()

    override fun handleMethodArgumentNotValid( // just search for this method to get
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
//        return super.handleMethodArgumentNotValid(ex, headers, status, request) This is the function we are going to override in order to catch any exception thats run by bean validation
//        logger.error("MethodArgumentNotValidException observed : ${ex.message}",ex)
        val errors = ex.bindingResult.allErrors // This holds all the errors
            .map { error-> error.defaultMessage!! } // This will return default message called " CourseDTO.name must not be blank" to the user
            .sorted() // Sort the result so that we get same response if there are multiple error observe as a call. So that user always get the message in the same order.

//        logger.info("Errors : $errors")

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errors.joinToString(", "){it}) //sorted() will convert it into list
    }

    @ExceptionHandler(Exception::class) //This will take care of, function get invoked for any kind of runtime exception. Exception should be part of java.lang.Exception
    fun handleAllExceptions(ex: Exception, request:WebRequest) : ResponseEntity<Any>{ // take input as exception and WebRequest and return ReposnseEntity
        logger.error("Exception observed : ${ex.message}",ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ex.message)

    }

    @ExceptionHandler(InstructorNotValidException::class) //This will take care of, function get invoked for any kind of runtime exception. Exception should be part of java.lang.Exception
    fun handleInstructorNotValidException(ex: InstructorNotValidException, request:WebRequest) : ResponseEntity<Any>{ // take input as exception and WebRequest and return ReposnseEntity
        logger.error("Exception observed : ${ex.message}",ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ex.message)

    }

}