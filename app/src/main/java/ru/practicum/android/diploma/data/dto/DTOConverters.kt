package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class DTOConverters {
    fun map(vacancyDTO: VacancyDTO): Vacancy = Vacancy(
        id = vacancyDTO.id,
        name = vacancyDTO.name,
        salary = map(vacancyDTO.salary),
        city = vacancyDTO.area.name,
        employerName = vacancyDTO.employer.name
    )

    fun map(salaryDTO: SalaryDTO?): Salary? = if (salaryDTO != null) {
        Salary(
            currency = salaryDTO.currency,
            from = salaryDTO.from,
            to = salaryDTO.to,
            gross = salaryDTO.gross
        )
    } else {
        null
    }

    fun map(currencyDTO: CurrencyDTO): Currency = Currency(
        code = currencyDTO.code, name = currencyDTO.name, abbr = currencyDTO.abbr
    )
}
