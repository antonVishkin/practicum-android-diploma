package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.SalaryDTO
import ru.practicum.android.diploma.data.dto.SearchRequest
import ru.practicum.android.diploma.data.dto.VacanciesDTOConverters
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.search.SearchRepository
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchRepositoryImpl(private val retrofitNetworkClient: RetrofitNetworkClient,private val vacanciesDTOConverters: VacanciesDTOConverters) : SearchRepository {
    override fun searchRequest(options: Map<String, String>): Flow<Result<List<Vacancy>>> = flow {
        val response = retrofitNetworkClient.doRequest(SearchRequest(options))
        response.onSuccess { list ->
            if (!list.isNullOrEmpty())
                emit(
                    Result.success(list.map {vacanciesDTOConverters.map(it) })
                )
            else
                emit(
                    Result.success(listOf())
                )
        }
        response.onFailure {
            emit(Result.failure(it))
        }
    }
}
