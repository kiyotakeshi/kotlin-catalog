package com.kiyotakeshi.kotlin.catalog.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GreetingService {

    // message variable will be initialized after tha application starts up
    @Value("\${message}")
    lateinit var message: String

    fun retrieveGreeting(name: String): String = "$name, $message"
}
