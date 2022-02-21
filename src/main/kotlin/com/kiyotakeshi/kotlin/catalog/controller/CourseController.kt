package com.kiyotakeshi.kotlin.catalog.controller

import com.kiyotakeshi.kotlin.catalog.dto.CourseDto
import com.kiyotakeshi.kotlin.catalog.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/courses")
class CourseController(
    val courseService: CourseService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody courseDto: CourseDto): CourseDto {
        return courseService.addCourse(courseDto)
    }

    @GetMapping
    fun retrieveAllCourses(): List<CourseDto> = courseService.retrieveAllCourses()

    @PutMapping("/{courseId}")
    fun updateCourse(
        @RequestBody courseDto: CourseDto,
        @PathVariable("courseId") courseId: Int
    ): CourseDto = courseService.updateCourse(courseId, courseDto)
}
