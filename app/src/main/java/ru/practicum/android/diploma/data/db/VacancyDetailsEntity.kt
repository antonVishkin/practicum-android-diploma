package ru.practicum.android.diploma.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_vacancy_details")
data class VacancyDetailsEntity(
    @PrimaryKey
    val id: String,
    val vacancyName: String,
    val companyName: String,
    @ColumnInfo(name = "alternate_url")
    val alternateUrl: String?,
    @ColumnInfo(name = "logo_url")
    val logoUrl: String?,
    val city: String?,
    val employment: String?,
    val experience: String?,
    @Embedded(prefix = "salary_")
    val salary: SalaryEntity?,
    val description: String?,
    @ColumnInfo(name = "key_skills")
    val keySkills: List<String?>,
    @Embedded(prefix = "contacts_")
    val contacts: ContactsEntity?,
    val comment: String?,
    val schedule: String?,
    val address: String?
)
