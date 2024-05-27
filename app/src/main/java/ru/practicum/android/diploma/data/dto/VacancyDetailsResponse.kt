package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDetailsResponse(
    val id: String,
    val name: String,
    val salary: SalaryDTO?,
    val employer: EmployerDTO?,
    val area: AreaDTO,
    val experience: ExperienceDTO,
    val description: String,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDTO>,
    val contacts: ContactsDTO?,
    @SerializedName("alternate_url")
    val alternateUrl: String // Добавлено новое поле
) : Response()
