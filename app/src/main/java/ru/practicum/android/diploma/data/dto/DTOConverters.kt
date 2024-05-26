package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

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

    fun map(detailsResponse: VacancyDetailsResponse): VacancyDetails {
        return VacancyDetails(
            id = detailsResponse.id,
            name = detailsResponse.name,
            salary = map(detailsResponse.salary),
            employerName = detailsResponse.employer?.name,
            logoUrl = detailsResponse.employer?.logoUrls?.original,
            areaName = detailsResponse.area.name,
            experienceName = detailsResponse.experience.name,
            description = detailsResponse.description,
            keySkills = detailsResponse.keySkills.map { it.name },
            contactPerson = detailsResponse.contacts?.name,
            email = detailsResponse.contacts?.email,
            phones = detailsResponse.contacts?.phones?.map { it.formatted }
        )
    }

}
