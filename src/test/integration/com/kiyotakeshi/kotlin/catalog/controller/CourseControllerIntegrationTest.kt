package com.kiyotakeshi.kotlin.catalog.controller

import com.kiyotakeshi.kotlin.catalog.controller.util.courseEntityList
import com.kiyotakeshi.kotlin.catalog.controller.util.createInstructorEntity
import com.kiyotakeshi.kotlin.catalog.dto.CourseDto
import com.kiyotakeshi.kotlin.catalog.entity.Course
import com.kiyotakeshi.kotlin.catalog.repository.CourseRepository
import com.kiyotakeshi.kotlin.catalog.repository.InstructorRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    internal fun setUp() {
        instructorRepository.deleteAll()
        courseRepository.deleteAll()

        val instructor = createInstructorEntity()
        instructorRepository.save(instructor)

        val courses = courseEntityList(instructor)
        courseRepository.saveAll(courses)
    }

    @Test
    fun addCourse() {

        val instructor = instructorRepository.findAll().first()
        val courseDto = CourseDto(null, "kotlin REST API", "API", instructor.id)

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
    fun retrieveAllCoursesByName() {

        val url = UriComponentsBuilder.fromUriString("/v1/courses")
            .queryParam("course_name", "terraform")
            .toUriString()

        val actual = webTestClient
            .get()
            .uri(url)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()
            .responseBody

        println("course dtos : $actual")
        Assertions.assertEquals(1, actual!!.size)
    }

    @Test
    fun updateCourse() {
        val instructor = instructorRepository.findAll().first()

        val course = Course(null, "create API with kotlin(Spring Boot)", "API", instructor)
        courseRepository.save(course)

        val updateDto = CourseDto(null, "update", "API", course.instructor!!.id)

        val actual = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", course.id)
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("update", actual!!.name)
        Assertions.assertEquals("API", actual.category)
    }

    @Test
    fun deleteCourse() {
        val instructor = instructorRepository.findAll().first()

        val course = Course(null, "create API with kotlin(Spring Boot)", "API", instructor)
        courseRepository.save(course)

        webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", course.id)
            .exchange()
            .expectStatus().isNoContent
    }
}
