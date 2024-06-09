package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.service.CourseService
import com.kotlinspring.service.GreetingService
import com.kotlinspring.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.assertj.core.internal.ErrorMessages
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUniteTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock : CourseService

    @Test
    fun addCouse(){

        val courseDTO = CourseDTO(null,"Build Restful APIs using Kotlin and SpringBoot","Development11",1)

       every {courseServiceMock.addCourse(any())} returns courseDTO(id = 1)

        val savedCourseDTO = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue{
            savedCourseDTO!!.id != null
        }

    }

    @Test
    fun addCouse_validation(){

        val courseDTO = CourseDTO(null,"","",1)

        every {courseServiceMock.addCourse(any())} returns courseDTO(id = 1)

        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("CourseDTO.category must of be blank, CourseDTO.name must not be blank",response)

    }

    @Test
    fun addCouse_runtimeException(){

        val courseDTO = CourseDTO(null,"Build Restful APIs using Kotlin and SpringBoot","Development11",1)

        val ErrorMessage = "Unexcepted Error Occurred"
        every {courseServiceMock.addCourse(any())} throws RuntimeException(ErrorMessage)

        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals(ErrorMessage,response)

    }

    @Test
    fun retrieveAllCourses(){

        every { courseServiceMock.retrieveAllCourses(any()) }.returnsMany(
            listOf(courseDTO(id=1),
                courseDTO(id=2,name="Build Reactive Microservices using Spring WebFlux/SpringBoot"))
        )

        val courseDTOs = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        println("courseDTOs : $courseDTOs")
        assertEquals(2,courseDTOs!!.size)
    }

    @Test
    fun updateCourse(){

        val course = Course(null,
            "Build RestFul APis using SpringBoot and Kotlin", "Development")

        every {courseServiceMock.updateCourse(any(),any())} returns  courseDTO(100, "Build RestFul APis using SpringBoot and Kotlin11")

        val courseUpdateDTO = CourseDTO(null,
            "Build RestFul APis using SpringBoot and Kotlin11", "Development")

        val updatedCourse = webTestClient
            .put()
            .uri("/v1/courses/{courseId}",100)
            .bodyValue(courseUpdateDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("Build RestFul APis using SpringBoot and Kotlin11",updatedCourse!!.name)

    }

    @Test
    fun deleteCourse(){

        every {courseServiceMock.deleteCourse(any())} just runs

        val updatedCourse = webTestClient
            .delete()
            .uri("/v1/courses/{courseId}",100)
            .exchange()
            .expectStatus().isNoContent

    }

}