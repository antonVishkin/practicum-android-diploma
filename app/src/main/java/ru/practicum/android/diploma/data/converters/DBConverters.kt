package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.CurrencyDictionaryEntity
import ru.practicum.android.diploma.data.db.SalaryEntity
import ru.practicum.android.diploma.data.db.VacancyDetailsEntity
import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

class DBConverters {
    fun map(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            vacancyName = vacancy.vacancyName,
            companyName = vacancy.companyName,
            alternateUrl = vacancy.alternateUrl,
            logoUrl = vacancy.logoUrl,
            city = vacancy.city,
            employment = vacancy.employment,
            experience = vacancy.experience,
            salary = SalaryEntity(
                vacancy.salary?.currency,
                vacancy.salary?.from,
                vacancy.salary?.gross,
                vacancy.salary?.to,
            ),
            description = vacancy.description,
            keySkills = vacancy.keySkills,
            contacts = null, // ИСПРААВИТЬ
            comment = vacancy.comment,
            schedule = vacancy.schedule,
            address = vacancy.address
        )
    }

    fun map(vacancy: VacancyEntity): Vacancy {
        return Vacancy(
            id = vacancy.id,
            vacancyName = vacancy.vacancyName,
            companyName = vacancy.companyName,
            alternateUrl = vacancy.alternateUrl,
            logoUrl = vacancy.logoUrl,
            city = vacancy.city,
            employment = vacancy.employment,
            experience = vacancy.experience,
            salary = Salary(vacancy.salary?.currency, vacancy.salary?.from, vacancy.salary?.gross, vacancy.salary?.to),
            description = vacancy.description,
            keySkills = vacancy.keySkills,
            contacts = null, // ИСПРААВИТЬ
            comment = vacancy.comment,
            schedule = vacancy.schedule,
            address = vacancy.address
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
            phones = vacancyDetails.phones.toString(),
            alternateUrl = vacancyDetails.alternateUrl
        )
    }

    fun map(vacancyDetailsEntity: VacancyDetailsEntity): VacancyDetails {
        return VacancyDetails(
            id = vacancyDetailsEntity.id,
            name = vacancyDetailsEntity.name,
            salary = Salary(null, null, null, null),
            employerName = vacancyDetailsEntity.employerName,
            logoUrl = vacancyDetailsEntity.logoUrl,
            areaName = vacancyDetailsEntity.areaName,
            experienceName = vacancyDetailsEntity.experienceName,
            description = vacancyDetailsEntity.description,
            keySkills = listOf(),
            contactPerson = vacancyDetailsEntity.contactPerson,
            email = vacancyDetailsEntity.email,
            phones = listOf(),
            alternateUrl = vacancyDetailsEntity.alternateUrl
        )
    }

    fun mapDetailsToVacancy(vacancyDetails: VacancyDetails): Vacancy {
        return Vacancy(
            id = vacancyDetails.id,
            vacancyName = vacancyDetails.name,
            companyName = vacancyDetails.employerName.toString(),
            alternateUrl = vacancyDetails.alternateUrl,
            logoUrl = vacancyDetails.logoUrl,
            city = vacancyDetails.areaName,
            employment = "",
            experience = vacancyDetails.experienceName,
            salary = vacancyDetails.salary,
            description = vacancyDetails.description,
            keySkills = vacancyDetails.keySkills,
            contacts = Contacts(vacancyDetails.email, vacancyDetails.contactPerson, vacancyDetails.phones),
            comment = "",
            schedule = "",
            address = ""
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
