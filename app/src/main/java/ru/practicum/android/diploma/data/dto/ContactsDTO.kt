package ru.practicum.android.diploma.data.dto

data class ContactsDTO(
    val name: String?,
    val email: String?,
    val phones: List<PhoneDTO>?
)
