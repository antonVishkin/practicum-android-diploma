package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.DTOConverters
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.filtration.country.CountryRepository
import ru.practicum.android.diploma.domain.api.filtration.region.RegionsRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.SearchResultData

class RegionsRepositoryImpl(private val networkClient: NetworkClient) : RegionsRepository {
    private val dtoConverters = DTOConverters()

    override suspend fun getRegions(selectedCountryId: String?): Flow<SearchResultData<List<Country>>> = flow {
        val response = networkClient.getRegions(selectedCountryId)
        val data = response?.getOrNull()
        val error = response?.exceptionOrNull()
        when {
            data != null -> {
                emit(SearchResultData.Data(dtoConverters.mapToListCountries(data)))
            }

            error is HttpException -> {
                emit(SearchResultData.ServerError(R.string.search_server_error))
            }

            else -> {
                emit(SearchResultData.NoConnection(R.string.search_no_connection))
            }
        }
    }

    // Implement the abstract method from RegionsRepository if necessary
}
