package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_vacancy_details")
data class VacancyDetailsEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val logoUrl: String?,
    val employerName: String,
    val cityName: String,
    val experienceName: String,
    val description: String,
    val responsibilities: String,
    val requirements: String,
    val conditions: String,
    val keySkills: String,
    val contacts: String,
    val comments: String?
)
