package ru.practicum.android.diploma.domain.models

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.AreaDTO
import ru.practicum.android.diploma.data.dto.ContactsDTO
import ru.practicum.android.diploma.data.dto.EmployerDTO
import ru.practicum.android.diploma.data.dto.ExperienceDTO
import ru.practicum.android.diploma.data.dto.KeySkillDTO

data class VacancyDetails(
    val id: String,
    val name: String,
    val salary: Salary?,
    val employerName: String?,
    val logoUrl:String?,
    val areaName: String,
    val experienceName: String,
    val description: String,
    val keySkills: List<String>,
    val contactPerson: String?,
    val email: String?,
    val phones: List<String>?
)
