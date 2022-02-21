package com.kiyotakeshi.kotlin.catalog.service

import com.kiyotakeshi.kotlin.catalog.dto.CourseDto
import com.kiyotakeshi.kotlin.catalog.entity.Course
import com.kiyotakeshi.kotlin.catalog.exception.CourseNotFoundException
import com.kiyotakeshi.kotlin.catalog.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(
    val courseRepository: CourseRepository
) {
    companion object : KLogging()

    fun addCourse(courseDto: CourseDto): CourseDto {
        val courseEntity = courseDto.let {
            Course(null, it.name, it.category)
        }
        courseRepository.save(courseEntity)

        logger.info("saved course is : $courseEntity")

        return courseEntity.let {
            CourseDto(it.id, it.name, it.category)
        }
    }

    fun retrieveAllCourses(): List<CourseDto> {
        return courseRepository.findAll()
            .map {
                CourseDto(it.id, it.name, it.category)
            }
    }

    fun updateCourse(courseId: Int, courseDto: CourseDto): CourseDto {
        val foundedCourse = courseRepository.findById(courseId)
        return if (foundedCourse.isPresent) {
            foundedCourse.get()
                .let {
                    it.name = courseDto.name
                    it.category = courseDto.category
                    courseRepository.save(it)
                    CourseDto(it.id, it.name, it.category)
                }
        } else {
            throw CourseNotFoundException("course not found(id :${courseId})")
        }
    }
}
