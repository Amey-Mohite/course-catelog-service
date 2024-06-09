package com.kotlinspring.dto

import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotBlank


data class InstructorDTO(
    val id: Int?,
    @get:NotBlank(message = "InstructorDTO.name must not be blank")
    var name: String
)
