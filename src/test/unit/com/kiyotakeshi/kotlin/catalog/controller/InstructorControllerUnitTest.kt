package com.kiyotakeshi.kotlin.catalog.controller

import com.kiyotakeshi.kotlin.catalog.dto.InstructorDto
import com.kiyotakeshi.kotlin.catalog.service.InstructorService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebTestClient
class InstructorControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorServiceMockk: InstructorService

    @Test
    fun createInstructor() {
        val instructorDto = InstructorDto(null, "mike popcorn")

        every {
            instructorServiceMockk.createInstructor(any())
        } returns InstructorDto(1, "mike popcorn")

        val actual = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDto::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            actual!!.id != null
        }
    }

    @Test
    fun createInstructorValidation() {
        val instructorDto = InstructorDto(null, "")

        every {
            instructorServiceMockk.createInstructor(any())
        } returns InstructorDto(1, "")

        val actual = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDto)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("instructor dto name must not be blank", actual)
    }
}
