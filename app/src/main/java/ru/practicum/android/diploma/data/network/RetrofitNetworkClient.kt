package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.SearchRequest

class RetrofitNetworkClient(private val headHunterApi: HeadHunterApi):NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (dto !is SearchRequest)
            return Response().apply { resultCode = CLIENT_ERROR_RESULT_CODE }
            headHunterApi.searchVacancies(dto.options)
        return withContext(Dispatchers.IO){
            try {
                val response = headHunterApi.searchVacancies(dto.options)
                response.apply { resultCode = CLIENT_SUCCESS_RESULT_CODE }
            } catch (e:Throwable){
                Response().apply {resultCode = CLIENT_ERROR_RESULT_CODE}
            }
        }
    }

    companion object {
        const val CLIENT_ERROR_RESULT_CODE = 400
        const val CLIENT_SUCCESS_RESULT_CODE = 200
    }
}
