package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.SearchRequest
import ru.practicum.android.diploma.data.dto.VacancyDTO
import java.io.IOException

class RetrofitNetworkClient(private val headHunterApi: HeadHunterApi) : NetworkClient {
    override suspend fun doRequest(dto: Any): Result<List<VacancyDTO>> {
        return if (dto !is SearchRequest) {
            Result.failure(Throwable("неверный запрос"))
        } else {
            withContext(Dispatchers.IO) {
                try {
                    val response = headHunterApi.searchVacancies("Bearer " + BuildConfig.HH_ACCESS_TOKEN, dto.options)
                    Log.v("VACANCY", "responce" + response.items.toString())
                    Result.success(response.items)
                } catch (e: IOException) {
                    Log.e("NETWORK ERROR", e.toString())
                    Result.failure(e)
                }
            }
        }
    }

    companion object {
        const val CLIENT_ERROR_RESULT_CODE = 400
        const val CLIENT_SUCCESS_RESULT_CODE = 200
    }
}
