package com.kiyotakeshi.kotlin.catalog.controller.util

import com.kiyotakeshi.kotlin.catalog.dto.CourseDto
import com.kiyotakeshi.kotlin.catalog.entity.Course
import com.kiyotakeshi.kotlin.catalog.entity.Instructor

fun createInstructorEntity(name: String = "mike popcorn") = Instructor(null, name)

fun courseEntityList(instructor: Instructor? = null) = listOf(
    Course(null, "create API with kotlin(Spring Boot)", "API", instructor),
    Course(null, "create API with kotlin(Ktor)", "API", instructor),
    Course(null, "create infrastructure with terraform", "IaC", instructor)
)

fun createCourseDto(
    id: Int? = null,
    name: String = "REST API with kotlin and Spring boot",
    category: String = "Development",
    instructorId: Int? = 1
) = CourseDto(
    id,
    name,
    category,
    instructorId
)
