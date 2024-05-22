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

    // Details

    fun map(vacancyDetails: VacancyDetails): VacancyDetailsEntity {
        return VacancyDetailsEntity(
            id = vacancyDetails.id,
            name = vacancyDetails.name,
            logoUrl = vacancyDetails.logoUrl,
            employerName = vacancyDetails.employerName,
            cityName = vacancyDetails.cityName,
            experienceName = vacancyDetails.experienceName,
            description = vacancyDetails.description,
            responsibilities = vacancyDetails.responsibilities,
            requirements = vacancyDetails.requirements,
            conditions = vacancyDetails.conditions,
            keySkills = null.toString(),
            contacts = vacancyDetails.contacts,
            comments = vacancyDetails.comments
        )
    }

    fun map(vacancyDetailsEntity: VacancyDetailsEntity): VacancyDetails {
        return VacancyDetails(
            id = vacancyDetailsEntity.id,
            name = vacancyDetailsEntity.name,
            logoUrl = vacancyDetailsEntity.logoUrl,
            employerName = vacancyDetailsEntity.employerName,
            cityName = vacancyDetailsEntity.cityName,
            experienceName = vacancyDetailsEntity.experienceName,
            description = vacancyDetailsEntity.description,
            responsibilities = vacancyDetailsEntity.responsibilities,
            requirements = vacancyDetailsEntity.requirements,
            conditions = vacancyDetailsEntity.conditions,
            keySkills = null,
            contacts = vacancyDetailsEntity.contacts,
            comments = vacancyDetailsEntity.comments
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
}
