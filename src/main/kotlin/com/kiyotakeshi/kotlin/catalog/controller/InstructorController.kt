package com.kiyotakeshi.kotlin.catalog.controller

import com.kiyotakeshi.kotlin.catalog.dto.InstructorDto
import com.kiyotakeshi.kotlin.catalog.service.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/instructors")
@Validated
class InstructorController(
    val instructorService: InstructorService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createInstructor(@RequestBody @Validated instructorDto: InstructorDto): InstructorDto =
        instructorService.createInstructor(instructorDto)
}
