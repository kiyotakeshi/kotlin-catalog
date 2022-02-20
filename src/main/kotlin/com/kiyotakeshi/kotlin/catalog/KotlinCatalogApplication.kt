package com.kiyotakeshi.kotlin.catalog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinCatalogApplication

fun main(args: Array<String>) {
    runApplication<KotlinCatalogApplication>(*args)
}
