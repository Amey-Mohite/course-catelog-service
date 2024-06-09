package com.kotlinspring.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping


@Service
class GreetingService {
    @Value("\${message}")
    lateinit var message: String
    fun retrieveGreeting1(name:String) = "Hello $name, $message"
}