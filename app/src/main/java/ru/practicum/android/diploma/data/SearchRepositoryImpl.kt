package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.SearchRequest
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.search.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchRepositoryImpl(private val retrofitNetworkClient: RetrofitNetworkClient) : SearchRepository {
    override fun searchRequest(options: Map<String, String>): Flow<Result<List<Vacancy>>> = flow {
        val response = retrofitNetworkClient.doRequest(SearchRequest(options))
        response.onSuccess { list ->
            if (!list.isNullOrEmpty())
                emit(
                    Result.success(list.map { Vacancy(
                    id = it.id,
                    name = it.name,
                    salary = null,
                    city = null,
                    employerName = "Test"
                    ) })
                )
        }
        response.onFailure {
            emit(Result.failure(it))
        }
    }
}
