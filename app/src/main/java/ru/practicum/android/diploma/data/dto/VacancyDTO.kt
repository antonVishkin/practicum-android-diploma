package ru.practicum.android.diploma.data.dto

import AreaDTO

data class VacancyDTO(
    val id: String,
    val name: String,
    val employer: EmployerDTO,
    val salary: SalaryDTO,
    val area: AreaDTO,
)
