package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.service.CourseService
import jakarta.validation.Valid
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/courses")
@Validated
class CourseController(val courseService: CourseService) {
    companion object : KLogging()
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDTO: CourseDTO) : CourseDTO {
        logger.info("Received CourseDTO: $courseDTO")
        return courseService.addCourse(courseDTO)
    }

    @GetMapping
    fun retrieveAllCourses(@RequestParam("course_name", required = false) courseName:String?) : List<CourseDTO> = courseService.retrieveAllCourses(courseName)

    @PutMapping("/{course_id}")
    fun updateCourse(@RequestBody courseDTO: CourseDTO, @PathVariable("course_id") courseId : Int)  = courseService.updateCourse(courseId, courseDTO)

    @DeleteMapping("/{course_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("course_id") courseId : Int)  = courseService.deleteCourse(courseId)


}