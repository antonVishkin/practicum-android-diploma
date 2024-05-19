package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.SearchRequest
import ru.practicum.android.diploma.data.dto.SearchResponse
import ru.practicum.android.diploma.data.dto.DTOConverters
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.search.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchRepositoryImpl(
    private val retrofitNetworkClient: RetrofitNetworkClient,
    private val dTOConverters: DTOConverters
) : SearchRepository {
    override fun searchRequest(options: Map<String, String>): Flow<Result<List<Vacancy>>> = flow {
        val response = retrofitNetworkClient.doRequest(SearchRequest(options))
        when(response.resultCode){
            200 -> {
                val list = (response as SearchResponse).items
                if (!list.isNullOrEmpty()) {
                    emit(
                        Result.success(list.map { dTOConverters.map(it) })
                    )
                } else {
                    emit(
                        Result.success(listOf())
                    )
                }
            }
            else ->{
                emit(
                    Result.failure(Throwable())
                )
            }
        }
    }
}
