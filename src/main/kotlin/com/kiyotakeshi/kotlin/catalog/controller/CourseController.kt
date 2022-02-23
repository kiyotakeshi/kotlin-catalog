package com.kiyotakeshi.kotlin.catalog.controller

import com.kiyotakeshi.kotlin.catalog.dto.CourseDto
import com.kiyotakeshi.kotlin.catalog.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/courses")
@Validated
class CourseController(
    val courseService: CourseService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDto: CourseDto): CourseDto {
        return courseService.addCourse(courseDto)
    }

    @GetMapping
    fun retrieveAllCourses(@RequestParam("course_name", required = false) courseName: String?): List<CourseDto> =
        courseService.retrieveAllCourses(courseName)

    @PutMapping("/{courseId}")
    fun updateCourse(
        @RequestBody courseDto: CourseDto,
        @PathVariable("courseId") courseId: Int
    ): CourseDto = courseService.updateCourse(courseId, courseDto)

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("courseId") courseId: Int) = courseService.deleteCourse(courseId)
}
