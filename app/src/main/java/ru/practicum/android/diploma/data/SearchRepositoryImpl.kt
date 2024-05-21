package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.DTOConverters
import ru.practicum.android.diploma.data.dto.SearchRequest
import ru.practicum.android.diploma.data.dto.SearchResponse
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.search.SearchRepository
import ru.practicum.android.diploma.domain.models.VacancyPage

class SearchRepositoryImpl(
    private val retrofitNetworkClient: RetrofitNetworkClient,
    private val dTOConverters: DTOConverters
) : SearchRepository {
    override fun searchRequest(options: Map<String, String>): Flow<Result<VacancyPage>> = flow {
        val response = retrofitNetworkClient.doRequest(SearchRequest(options))
        when (response.resultCode) {
            CLIENT_SUCCESS_RESULT_CODE -> {
                val searchResponse = response as SearchResponse
                val list = searchResponse.items
                val page = searchResponse.page
                val pages = searchResponse.pages
                val found = searchResponse.found
                if (!list.isNullOrEmpty()) {
                    emit(
                        Result.success(VacancyPage(list.map { dTOConverters.map(it) }, page, pages, found))
                    )
                } else {
                    emit(
                        Result.success(VacancyPage(listOf(), -1, -1, -1))
                    )
                }
            }

            else -> {
                emit(
                    Result.failure(Throwable())
                )
            }
        }
    }

    companion object {
        const val CLIENT_SUCCESS_RESULT_CODE = 200
    }
}
