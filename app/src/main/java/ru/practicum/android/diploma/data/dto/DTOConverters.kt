package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.models.City
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Region
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
            phones = detailsResponse.contacts?.phones?.map { it.formatted },
            alternateUrl = detailsResponse.alternateUrl,
        )
    }

    fun mapToCountry(areaDTO: AreaDTO): Country = Country(
        id = areaDTO.id,
        name = areaDTO.name,
        regions = areaDTO.areas.map { mapToRegion(it) }
    )

    fun mapToRegion(areaDTO: AreaDTO): Region = Region(
        id = areaDTO.id,
        name = areaDTO.name,
        cities = areaDTO.areas.map { mapToCity(it) }
    )

    fun mapToCity(areaDTO: AreaDTO): City = City(
        id = areaDTO.id,
        name = areaDTO.name
    )

    fun mapToListCountries(areaDTOs: List<AreaDTO>): List<Country> {
        return areaDTOs.filter { it.parentId == null }.map { mapToCountry(it) }
    }

    fun mapToListRegions(areaDTOs: List<AreaDTO>, countryId: String): List<Region> {
        return if (countryId.isEmpty()) {
            convertTreeToList(areaDTOs).map { mapToRegion(it) }
        } else {
            val country = areaDTOs.find { it.id == countryId }
            if (country != null) {
                convertTreeToList(country.areas).map { mapToRegion(it) }
            } else {
                // Обработка случая, если страна с указанным countryId не найдена
                emptyList()
            }
        }
    }

    private fun convertTreeToList(areaDTOs: List<AreaDTO>): List<AreaDTO> {
        val result = mutableListOf<AreaDTO>()
        areaDTOs.forEach {
            if (it.areas.isEmpty()) {
                result.add(it)
            } else {
                result.add(AreaDTO(it.id, it.parentId, it.name, listOf()))
                result.addAll(convertTreeToList(it.areas))
            }
        }
        return result
    }

}

