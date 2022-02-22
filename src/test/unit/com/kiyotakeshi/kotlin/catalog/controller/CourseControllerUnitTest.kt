package com.kiyotakeshi.kotlin.catalog.controller

import com.kiyotakeshi.kotlin.catalog.controller.util.createCourseDto
import com.kiyotakeshi.kotlin.catalog.dto.CourseDto
import com.kiyotakeshi.kotlin.catalog.entity.Course
import com.kiyotakeshi.kotlin.catalog.service.CourseService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMockk: CourseService

    @Test
    fun addCourse() {
        val courseDto = CourseDto(null, "kotlin REST API", "API")

        every {
            courseServiceMockk.addCourse(any())
        } returns createCourseDto(id = 100)

        val actual = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            actual!!.id != null
        }
    }

    @Test
    fun retrieveAllCourses() {

        every {
            courseServiceMockk.retrieveAllCourses()
        }.returnsMany(
            listOf(
                createCourseDto(id = 101),
                createCourseDto(id = 102, name = "create API with kotlin(Ktor)"),
                createCourseDto(id = 103, name = "create infrastructure with terraform"),
            )
        )

        val actual = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()
            .responseBody

        println("course dtos : $actual")
        Assertions.assertEquals(3, actual!!.size)
    }

    @Test
    fun updateCourse() {

        every {
            courseServiceMockk.updateCourse(any(), any())
        } returns createCourseDto(id = 100, name = "update")

        val updateDto = CourseDto(null, "update", "API")

        val actual = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", 100)
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("update", actual!!.name)
    }

    @Test
    fun deleteCourse() {

        every {
            courseServiceMockk.deleteCourse(any())
        } just runs // mock function does not return any value

        webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", 100)
            .exchange()
            .expectStatus().isNoContent
    }
}
