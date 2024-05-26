package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.mapToListIndustries
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.industry.IndustryRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.SearchResultData

class IndustryRepositoryImpl(private val client: NetworkClient) : IndustryRepository {
    override suspend fun getIndustries(): Flow<SearchResultData<List<Industry>>> = flow {
        val response = client.getIndustries()
        val data = response?.getOrNull()
        val error = response?.exceptionOrNull()
        when {
            data != null -> {
                emit(SearchResultData.Data(mapToListIndustries(data)))
            }

            error is HttpException -> {
                emit(SearchResultData.ServerError(R.string.search_server_error))
            }

            else -> {
                emit(SearchResultData.NoConnection(R.string.search_no_connection))
            }
        }
    }
}
