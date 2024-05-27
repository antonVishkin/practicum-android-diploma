package ru.practicum.android.diploma.domain.api.filtration.region

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.SearchResultData

interface RegionInteractor {
    suspend fun getRegions(): Flow<SearchResultData<List<Country>>>
}
