package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_vacancy_details")
data class VacancyDetailsEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val salary: String,
    val employerName: String,
    val logoUrl: String,
    val areaName: String,
    val experienceName: String,
    val description: String,
    val keySkills: String,
    val contactPerson: String,
    val email: String,
    val phones: String
)
