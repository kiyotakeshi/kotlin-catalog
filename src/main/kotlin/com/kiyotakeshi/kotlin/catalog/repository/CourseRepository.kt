package com.kiyotakeshi.kotlin.catalog.repository

import com.kiyotakeshi.kotlin.catalog.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {
}
