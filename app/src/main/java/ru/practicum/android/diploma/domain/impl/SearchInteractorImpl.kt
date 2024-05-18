package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.search.SearchInteractor
import ru.practicum.android.diploma.domain.api.search.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchInteractorImpl(private val searchRepository: SearchRepository) : SearchInteractor {
    override fun searchVacancies(options: Map<String, String>): Flow<Result<List<Vacancy>>> {
        return searchRepository.searchRequest(options)
    }
}
