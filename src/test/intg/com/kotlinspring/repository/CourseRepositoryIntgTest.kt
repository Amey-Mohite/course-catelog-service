package com.kotlinspring.repository

import com.kotlinspring.util.PostgresSQLContrainerInitializer
import com.kotlinspring.util.courseEntityList
import com.kotlinspring.util.instructorEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.stream.Stream

@DataJpaTest // This going to spin up the slides of the application context and then make that DB layer available to test.
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryIntgTest : PostgresSQLContrainerInitializer() {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp(){
        courseRepository.deleteAll()
        instructorRepository.deleteAll()
        val instructor = instructorEntity()
        instructorRepository.save(instructor)

        val courses = courseEntityList(instructor)
        courseRepository.saveAll(courses)
    }

    @Test
    fun findByNameContaining(){
        val courses = courseRepository.findByNameContaining("SpringBoot")
        println("Courses :  $courses")
        Assertions.assertEquals(2,courses.size)
    }

    @Test
    fun findCoursesbyName(){
        val courses = courseRepository.findCoursesbyName("SpringBoot")
        println("Courses :  $courses")
        Assertions.assertEquals(2,courses.size)
    }

    @ParameterizedTest
    @MethodSource("courseAndSize") // Its going to provide an input and an output
    fun findCoursesbyName_approach2(name : String, expectedSize:Int){
        val courses = courseRepository.findCoursesbyName(name)
        println("Courses :  $courses")
        Assertions.assertEquals(expectedSize,courses.size)
    }

    companion object { // companion object does have idea about the courseAndSize function treated as static object, thats why it is necessary to treat that as statis function using JvmStatic keyword
        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {
            return Stream.of(Arguments.arguments("SpringBoot", 2), Arguments.arguments("Wiremock", 1))
        }
    } // this is used in order this function available to the method source

}