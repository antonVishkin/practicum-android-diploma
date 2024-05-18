package ru.practicum.android.diploma.domain.api.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchInteractor{
    fun searchVacancies(options:Map<String,String>):Flow<Result<List<Vacancy>>>
}
