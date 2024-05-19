package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.CurrencyRequest
import ru.practicum.android.diploma.data.dto.CurrencyResponse
import ru.practicum.android.diploma.data.dto.DTOConverters
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.dictionary.CurrencyRepository
import ru.practicum.android.diploma.domain.models.Currency

class CurrencyRepositoryImpl(
    private val retrofitNetworkClient: RetrofitNetworkClient,
    private val dtoConverters: DTOConverters
) : CurrencyRepository {
    override fun getCurrencyDictionary(): Flow<List<Currency>> = flow {
        val response = retrofitNetworkClient.doRequest(CurrencyRequest())
        when (response.resultCode) {
            200 -> {
                val list = (response as CurrencyResponse).currency
                if (list == null)
                    emit(listOf())
                else
                    emit(list.map { dtoConverters.map(it) })
            }

            else -> {
                emit(listOf())
            }
        }
    }

}
