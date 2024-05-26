package ru.practicum.android.diploma.domain.api.industry

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.SearchResultData

interface IndustryInteractor {
    suspend fun getIndustries(): Flow<SearchResultData<List<Industry>>>
}
