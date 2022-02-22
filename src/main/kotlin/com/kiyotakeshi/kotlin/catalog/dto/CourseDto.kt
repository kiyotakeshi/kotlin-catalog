package com.kiyotakeshi.kotlin.catalog.dto

import javax.validation.constraints.NotBlank

data class CourseDto(
    val id : Int?,

    // @see https://kotlinlang.org/docs/annotations.html#annotation-use-site-targets
    @get:NotBlank(message = "course dto name must not be blank")
    val name: String,

    @get:NotBlank(message = "course dto category must not be blank")
    val category: String
)
