package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NbaApplication

fun main(args: Array<String>) {
	runApplication<NbaApplication>(*args)
}
