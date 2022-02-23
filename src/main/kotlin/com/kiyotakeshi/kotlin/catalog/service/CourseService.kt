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

    fun retrieveAllCourses(courseName: String?): List<CourseDto> {
        val courses = courseName?.let {
            courseRepository.findByNameContaining(courseName)
        } ?: courseRepository.findAll()

        return courses.map {
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

    fun deleteCourse(courseId: Int) {
        val foundedCourse = courseRepository.findById(courseId)
        if (foundedCourse.isPresent) {
            foundedCourse.get()
                .let {
                    courseRepository.deleteById(courseId)
                }
        } else {
            throw CourseNotFoundException("course not found(id :${courseId})")
        }
    }
}
