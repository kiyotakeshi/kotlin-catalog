package com.kiyotakeshi.kotlin.catalog.controller

import com.kiyotakeshi.kotlin.catalog.dto.InstructorDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class InstructorControllerIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun createInstructor() {

        val instructorDto = InstructorDto(null, "mike popcorn")

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
}
