package com.coursecampus.coursecampus.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val location: String = "",
    val category: String = "",
    val qualification: String = "",
    val yearOfPassing: String = "",
    val university: String = "",
    val course: String = "",
    val cgpa: String = "",
    val interest: String = "",
    val goals: String = ""
)
