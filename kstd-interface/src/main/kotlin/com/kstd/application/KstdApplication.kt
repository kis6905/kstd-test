package com.kstd.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KstdApplication

fun main(args: Array<String>) {
    runApplication<KstdApplication>(*args)
}
