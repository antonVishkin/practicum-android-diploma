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
    val employer: EmployerDTO?,
    val area: AreaDTO,
    val experience: ExperienceDTO,
    val description: String,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDTO>,
    val contacts: ContactsDTO?,
)
