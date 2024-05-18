package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val salary: Salary?,
    val city: String?,
    val employerName: String,
)
