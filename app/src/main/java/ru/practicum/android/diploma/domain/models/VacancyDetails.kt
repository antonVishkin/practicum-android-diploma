package ru.practicum.android.diploma.domain.models

data class VacancyDetails(
    val id: String,
    val vacancyName: String,
    val companyName: String,
    val alternateUrl: String?,
    val logoUrl: String?,
    val city: String?,
    val employment: String?,
    val experience: String?,
    val salary: Salary?,
    val description: String?,
    val keySkills: List<String?>,
    val contacts: Contacts?,
    val comment: String?,
    val schedule: String?,
    val address: String?
)
