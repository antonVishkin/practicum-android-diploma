package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.CurrencyDictionaryEntity
import ru.practicum.android.diploma.data.db.SalaryEntity
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
            contacts = null, // ИСПРАВИТЬ
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
            contacts = null, // ИСПРАВИТЬ
            comment = vacancy.comment,
            schedule = vacancy.schedule,
            address = vacancy.address
        )
    }

    // Details

    fun map(vacancy: VacancyDetails): VacancyDetailsEntity {
        return VacancyDetailsEntity(
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
            contacts = null, // ИСПРАВИТЬ
            comment = vacancy.comment,
            schedule = vacancy.schedule,
            address = vacancy.address
        )
    }

    fun map(vacancy: VacancyDetailsEntity): VacancyDetails {
        return VacancyDetails(
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
            contacts = null, // ИСПРАВИТЬ
            comment = vacancy.comment,
            schedule = vacancy.schedule,
            address = vacancy.address
        )
    }

    fun mapDetailsToVacancy(vacancy: VacancyDetailsEntity): Vacancy {
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
            contacts = null, // ИСПРАВИТЬ
            comment = vacancy.comment,
            schedule = vacancy.schedule,
            address = vacancy.address
        )
    }

    fun mapVacancyToDetailsVacancy(vacancy: Vacancy): VacancyDetails {
        return VacancyDetails(
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
            contacts = null, // ИСПРАВИТЬ
            comment = vacancy.comment,
            schedule = vacancy.schedule,
            address = vacancy.address
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
