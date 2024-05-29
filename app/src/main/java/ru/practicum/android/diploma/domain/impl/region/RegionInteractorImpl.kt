package ru.practicum.android.diploma.domain.impl.region

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.filtration.country.CountryInteractor
import ru.practicum.android.diploma.domain.api.filtration.country.CountryRepository
import ru.practicum.android.diploma.domain.api.filtration.region.RegionsInteractor
import ru.practicum.android.diploma.domain.api.filtration.region.RegionsRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.SearchResultData

class RegionsInteractorImpl(private val repository: RegionsRepository) : RegionsInteractor {
    override suspend fun getRegions(): Flow<SearchResultData<List<Country>>> {
        return repository.getRegions(selectedCountryId = null)
    }
}
