package com.kotlinspring.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "CourseDTO.name must not be blank")
    val name: String,
    @get:NotBlank(message = "CourseDTO.category must of be blank")
    val category: String,   // Use camel case for consistency
    @get:NotNull(message = "CourseDTO.instructorId must not be blank")
    val instructorId: Int? = null,
)