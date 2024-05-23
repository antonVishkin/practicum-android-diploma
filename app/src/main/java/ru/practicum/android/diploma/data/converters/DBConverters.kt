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
            salary = "",
            employerName = vacancyDetails.employerName ?: "",
            logoUrl = vacancyDetails.logoUrl ?: "",
            areaName = vacancyDetails.areaName,
            experienceName = vacancyDetails.experienceName,
            description = vacancyDetails.description,
            keySkills = vacancyDetails.keySkills.toString(),
            contactPerson = vacancyDetails.contactPerson ?: "",
            email = vacancyDetails.email ?: "",
            phones = vacancyDetails.phones.toString()
        )
    }

    fun map(vacancyDetailsEntity: VacancyDetailsEntity): VacancyDetails {
        return VacancyDetails(
            id = vacancyDetailsEntity.id,
            name = vacancyDetailsEntity.name,
            salary = Salary(null, null, null),
            employerName = vacancyDetailsEntity.employerName,
            logoUrl = vacancyDetailsEntity.logoUrl,
            areaName = vacancyDetailsEntity.areaName,
            experienceName = vacancyDetailsEntity.experienceName,
            description = vacancyDetailsEntity.description,
            keySkills = listOf(),
            contactPerson = vacancyDetailsEntity.contactPerson,
            email = vacancyDetailsEntity.email,
            phones = listOf()
        )
    }

    fun mapCurrencyToEntity(currency: Currency): CurrencyDictionaryEntity = CurrencyDictionaryEntity(
        code = currency.code,
        name = currency.name,
        abbr = currency.abbr
    )

    fun mapCurrencyToEntity(currencyDictionaryEntity: CurrencyDictionaryEntity): Currency = Currency(
        code = currencyDictionaryEntity.code,
        name = currencyDictionaryEntity.name,
        abbr = currencyDictionaryEntity.abbr
    )
}
