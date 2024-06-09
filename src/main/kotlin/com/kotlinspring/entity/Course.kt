package com.kotlinspring.entity

import jakarta.persistence.*

@Entity
@Table(name="Courses")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Int?,
    var name : String,
    var category: String,
    @ManyToOne(fetch = FetchType.LAZY) // additional col only happen if you are pulling the instructor
    @JoinColumn(name = "INSTRUCTOR_ID", nullable = false) // When this table get created in the database, ther is going to be join column that connected course entity to the instructor entity, name of the column is instructor ID
    val instructor:Instructor? = null
){
    override fun toString(): String {
        return "Course(id='$id',name = '$name',category = '$category',instructor = '${instructor!!.id}')"
    }
}