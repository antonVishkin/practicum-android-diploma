package ru.practicum.android.diploma.domain.api.filtration.region

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.AreaDTO
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.SearchResultData

interface RegionsInteractor {
    suspend fun getRegions(selectedCountryId: String?): Flow<SearchResultData<List<Country>>>
}
