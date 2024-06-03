package ru.practicum.android.diploma.domain.impl.industry

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.industry.IndustryInteractor
import ru.practicum.android.diploma.domain.api.industry.IndustryRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.SearchResultData

class IndustryInteractorImpl(private val repository: IndustryRepository) : IndustryInteractor {
    override suspend fun getIndustries(): Flow<SearchResultData<List<Industry>>> {
        return repository.getIndustries()
    }
}
