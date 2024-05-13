package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_favorites_vacancies")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val salary: String?,
    val city: String?,
    val employerName: String
)