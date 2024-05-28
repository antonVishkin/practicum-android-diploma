package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converters.IndustryConverter
import ru.practicum.android.diploma.data.dto.IndustryRequest
import ru.practicum.android.diploma.data.dto.IndustryResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.industry.IndustryRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.SearchResultData

class IndustryRepositoryImpl(
    private val client: NetworkClient,
    private val converter: IndustryConverter
) : IndustryRepository {

    override suspend fun getIndustries(): Flow<SearchResultData<List<Industry>>> = flow {
        val response = client.doRequest(IndustryRequest())
        when (response.resultCode) {
            CLIENT_SUCCESS_RESULT_CODE -> {
                val list = (response as IndustryResponse).items
                emit(SearchResultData.Data(converter.mapToList(list)))
            }

            NO_CONNECTION_HTTP_CODE -> {
                emit(SearchResultData.Error(R.string.search_no_connection))
            }

            else -> {
                emit(SearchResultData.Error(R.string.search_server_error))
            }
        }
    }

    companion object {
        const val CLIENT_SUCCESS_RESULT_CODE = 200
        const val NO_CONNECTION_HTTP_CODE = 500
    }
}
