package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage

class DTOConverters {
    fun map(vacancyDTO: VacancyDTO): Vacancy = Vacancy(
        id = vacancyDTO.id,
        name = vacancyDTO.name,
        salary = map(vacancyDTO.salary),
        city = vacancyDTO.area.name,
        employerName = vacancyDTO.employer.name,
        urlImage = vacancyDTO.employer.logoUrls?.original
    )

    fun map(salaryDTO: SalaryDTO?): Salary? = if (salaryDTO != null) {
        Salary(
            currency = salaryDTO.currency,
            from = salaryDTO.from,
            to = salaryDTO.to,
        )
    } else {
        null
    }

    fun map(currencyDTO: CurrencyDTO): Currency = Currency(
        code = currencyDTO.code,
        name = currencyDTO.name,
        abbr = currencyDTO.abbr
    )

    fun map(detailsResponse: VacancyDetailsResponse): VacancyPage {
        return VacancyPage(
            id = detailsResponse.id,
            name = detailsResponse.name,
            logoUrl = detailsResponse.logoUrl,
            employerName = detailsResponse.employer.name,
            cityName = detailsResponse.area.name,
            experienceName = detailsResponse.experience.name,
            description = detailsResponse.description,
            responsibilities = detailsResponse.responsibilities,
            requirements = detailsResponse.requirements,
            conditions = detailsResponse.conditions,
            keySkills = detailsResponse.keySkills.map { it.name },
            contacts = detailsResponse.contacts.contactPerson,
            comments = detailsResponse.comments,
        )
    }
}
