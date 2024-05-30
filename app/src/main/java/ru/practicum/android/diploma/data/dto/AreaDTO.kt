package ru.practicum.android.diploma.data.dto

data class AreaDTO(
    val id: String,
    val parentId: String?,
    val name: String,
    val areas: List<AreaDTO>
)
