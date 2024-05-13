package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDBConverters {
    fun map(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            salary = vacancy.salary,
            city = vacancy.city,
            employerName = vacancy.employerName
        )
    }

    fun map(vacancy: VacancyEntity): Vacancy {
        return Vacancy(
            id = vacancy.id,
            name = vacancy.name,
            salary = vacancy.salary,
            city = vacancy.city,
            employerName = vacancy.employerName
        )
    }
}
