package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDBConverters {
    fun map(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            salaryCurrency = vacancy.salaryCurrency,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            city = vacancy.city,
            employerName = vacancy.employerName,
            urlImage = vacancy.urlImage
        )
    }

    fun map(vacancy: VacancyEntity): Vacancy {
        return Vacancy(
            id = vacancy.id,
            name = vacancy.name,
            salaryCurrency = vacancy.salaryCurrency,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            city = vacancy.city,
            employerName = vacancy.employerName,
            urlImage = vacancy.urlImage
        )
    }
}
