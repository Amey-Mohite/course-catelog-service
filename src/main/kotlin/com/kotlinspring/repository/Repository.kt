package com.kotlinspring.repository

import com.kotlinspring.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository:CrudRepository<Course, Int> {

    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    fun findByNameContaining(courseName:String) : List<Course> // Write in terms of camel casing

    // Using Native Query

    @Query(value = "Select * from courses where name like %?1%", nativeQuery = true)
    fun findCoursesbyName(courseName: String): List<Course>


}