package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.models.City
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Region

class DTOConverters {

    fun map(currencyDTO: CurrencyDTO): Currency = Currency(
        code = currencyDTO.code,
        name = currencyDTO.name,
        abbr = currencyDTO.abbr
    )

    fun mapToCountry(areaDTO: AreaDTO): Country = Country(
        id = areaDTO.id,
        name = areaDTO.name,
        regions = areaDTO.areas.map { mapToRegion(it) }
    )

    fun mapToRegion(areaDTO: AreaDTO): Region = Region(
        id = areaDTO.id,
        name = areaDTO.name,
        parentId = areaDTO.parentId,
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
            convertTreeToList(areaDTOs).filter { it.parentId != null && it.parentId != "1001" }.map { mapToRegion(it) }
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
