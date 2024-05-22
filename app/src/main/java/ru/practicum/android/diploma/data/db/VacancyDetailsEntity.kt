package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_vacancy_details")
data class VacancyDetailsEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
)
