package ru.practicum.android.diploma.data.dto

import retrofit2.http.Url

data class VacancyDTO (
    val id:String,
    val name:String,
    val employer:EmployerDTO,
    val url: String
)
