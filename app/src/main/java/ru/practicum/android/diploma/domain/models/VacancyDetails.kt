package ru.practicum.android.diploma.domain.models

data class VacancyDetails(
    val id: String,
    val name: String,
    val logoUrl: String?,
    val employerName: String,
    val cityName: String,
    val experienceName: String,
    val description: String,
    val responsibilities: String,
    val requirements: String,
    val conditions: String,
    val keySkills: List<String>?,
    val contacts: String,
    val comments: String?,
)