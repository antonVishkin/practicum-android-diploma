package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.SearchRequest
import java.io.IOException

class RetrofitNetworkClient(private val headHunterApi: HeadHunterApi) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        return if (dto !is SearchRequest) {
            Response().apply { resultCode = CLIENT_ERROR_RESULT_CODE }
        } else {
            withContext(Dispatchers.IO) {
                try {
                    val response = headHunterApi.searchVacancies(BuildConfig.HH_ACCESS_TOKEN, dto.options)
                    response.apply { resultCode = CLIENT_SUCCESS_RESULT_CODE }
                } catch (e: IOException) {
                    Log.e("NETWORK ERROR", e.toString())
                    Response().apply { resultCode = CLIENT_ERROR_RESULT_CODE }
                }
            }
        }
    }

    companion object {
        const val CLIENT_ERROR_RESULT_CODE = 400
        const val CLIENT_SUCCESS_RESULT_CODE = 200
    }
}
