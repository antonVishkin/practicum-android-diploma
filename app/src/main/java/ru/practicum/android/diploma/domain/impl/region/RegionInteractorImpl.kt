package ru.practicum.android.diploma.domain.impl.region

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.filtration.country.CountryInteractor
import ru.practicum.android.diploma.domain.api.filtration.country.CountryRepository
import ru.practicum.android.diploma.domain.api.filtration.region.RegionInteractor
import ru.practicum.android.diploma.domain.api.filtration.region.RegionRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.SearchResultData

class RegionInteractorImpl (private val repository: RegionRepository) : RegionInteractor {
    override suspend fun getRegions(): Flow<SearchResultData<List<Country>>> {
        return repository.getRegions()
    }
}
