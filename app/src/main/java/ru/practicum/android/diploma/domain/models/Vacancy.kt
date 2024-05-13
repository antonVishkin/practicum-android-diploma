package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val salary: String?,
    val city: String?,
    val employerName: String,
)
