package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.CurrencyDictionaryEntity
import ru.practicum.android.diploma.data.db.VacancyDetailsEntity
import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

class DBConverters {
    fun map(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            salaryCurrency = vacancy.salary?.currency,
            salaryFrom = vacancy.salary?.from,
            salaryTo = vacancy.salary?.to,
            city = vacancy.city,
            employerName = vacancy.employerName,
            urlImage = vacancy.urlImage
        )
    }

    fun map(vacancy: VacancyEntity): Vacancy {
        return Vacancy(
            id = vacancy.id,
            name = vacancy.name,
            salary = Salary(vacancy.salaryCurrency, vacancy.salaryFrom, vacancy.salaryTo),
            city = vacancy.city,
            employerName = vacancy.employerName,
            urlImage = vacancy.urlImage
        )
    }

    fun map(currency: Currency): CurrencyDictionaryEntity = CurrencyDictionaryEntity(
        code = currency.code,
        name = currency.name,
        abbr = currency.abbr
    )

    fun map(currencyDictionaryEntity: CurrencyDictionaryEntity): Currency = Currency(
        code = currencyDictionaryEntity.code,
        name = currencyDictionaryEntity.name,
        abbr = currencyDictionaryEntity.abbr
    )

    fun map(vacancyDetails: VacancyDetails): VacancyDetailsEntity = VacancyDetailsEntity(
        id = vacancyDetails.id,
        name = vacancyDetails.name,
        description = vacancyDetails.description,
    )

    fun map(vacancyDetailsEntity: VacancyDetailsEntity): VacancyDetails = VacancyDetails(
        id = vacancyDetailsEntity.id,
        name = vacancyDetailsEntity.name,
        logoUrl = null,
        employerName = "",
        cityName = "",
        experienceName = "",
        description = "",
        responsibilities = "",
        requirements = "",
        conditions = "",
        keySkills = listOf(),
        contacts = "",
        comments = null,
    )
}
