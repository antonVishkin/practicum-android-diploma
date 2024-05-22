package ru.practicum.android.diploma.data.dto

data class VacancyDetailsRequest(val vacancyId: String)

data class VacancyDetailsResponse(
    val id: String,
    val name: String,
    val logoUrl: String?,
    val employer: Employer,
    val area: Area,
    val experience: Experience,
    val description: String,
    val responsibilities: String,
    val requirements: String,
    val conditions: String,
    val keySkills: List<KeySkill>,
    val contacts: Contacts,
    val comments: String?
) : Response()

data class Employer(val name: String)
data class Area(val name: String)
data class Experience(val name: String)
data class KeySkill(val name: String)
data class Contacts(
    val contactPerson: String,
    val email: String,
    val phone: String
)
