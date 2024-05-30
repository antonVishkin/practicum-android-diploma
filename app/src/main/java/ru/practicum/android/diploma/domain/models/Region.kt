package ru.practicum.android.diploma.domain.models

data class Region(
    val id: String,
    val name: String,
    val cities: List<City>
)
/*data class Region(
    val id: String,
    val name: String,
    val parentId: String,
)*/
