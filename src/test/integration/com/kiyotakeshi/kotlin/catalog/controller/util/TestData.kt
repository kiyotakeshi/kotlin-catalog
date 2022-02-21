package com.kiyotakeshi.kotlin.catalog.controller.util

import com.kiyotakeshi.kotlin.catalog.entity.Course

fun courseEntityList() = listOf(
    Course(
        null,
        "create API with kotlin(Spring Boot)", "API"
    ),
    Course(
        null,
        "create API with kotlin(Ktor)", "API"
    ),
    Course(
        null,
        "create infrastructure with terraform", "IaC"
    )
)

