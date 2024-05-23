package ru.practicum.android.diploma.domain.models

data class VacancyDetails(
    val id: String,
    val name: String,
    val salary: Salary?,
    val employerName: String?,
    val logoUrl: String?,
    val areaName: String,
    val experienceName: String,
    val description: String,
    val keySkills: List<String>,
    val contactPerson: String?,
    val email: String?,
    val phones: List<String>?
)
