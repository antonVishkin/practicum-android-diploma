package ru.practicum.android.diploma.data.dto

data class VacancyDTO(
    val id: String,
    val name: String,
    val employer: EmployerDTO,
    val url: String
)
