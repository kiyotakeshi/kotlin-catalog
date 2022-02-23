package com.kiyotakeshi.kotlin.catalog.dto

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

data class InstructorDto(
    val id: Int?,

    @get:NotBlank(message = "instructor dto name must not be blank")
    var name: String
)
