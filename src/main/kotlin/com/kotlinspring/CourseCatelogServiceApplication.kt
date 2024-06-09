package com.kotlinspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CourseCatelogServiceApplication

fun main(args: Array<String>) {
	runApplication<CourseCatelogServiceApplication>(*args)
}
