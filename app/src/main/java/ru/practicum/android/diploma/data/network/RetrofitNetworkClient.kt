package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.AreaDTO
import ru.practicum.android.diploma.data.dto.CurrencyRequest
import ru.practicum.android.diploma.data.dto.CurrencyResponse
import ru.practicum.android.diploma.data.dto.ExperienceDTO
import ru.practicum.android.diploma.data.dto.IndustryResponse
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.SearchRequest
import ru.practicum.android.diploma.data.dto.SearchResponse
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

class RetrofitNetworkClient(
    private val headHunterApi: HeadHunterApi,
    private val context: Context
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = NO_INTERNET_RESULT_CODE }
        }
        return when (dto) {
            is SearchRequest -> {
                withContext(Dispatchers.IO) {
                    doSearchRequest(dto.options)
                }
            }

            is CurrencyRequest -> {
                withContext(Dispatchers.IO) {
                    doCurrencyRequest()
                }
            }

            is VacancyDetailsRequest -> {
                withContext(Dispatchers.IO) {
                    doVacancyDetailsRequest(dto.vacancyId)
                }
            }

            else -> Response().apply { resultCode = CLIENT_ERROR_RESULT_CODE }
        }
    }

    private suspend fun doVacancyDetailsRequest(vacancyId: String): Response {
        try {
            val response =
                headHunterApi.getVacancyDetails(BEARER_TOKEN, vacancyId)
            return VacancyDetailsResponse(
                response.id,
                response.name,
                response.salary,
                response.employer,
                response.area,
                response.experience,
                response.description,
                response.keySkills,
                response.contacts,
                response.alternateUrl,
            ).apply { resultCode = CLIENT_SUCCESS_RESULT_CODE }
        } catch (e: IOException) {
            Log.e(NETWORK_ERROR, e.toString())
            return createEmptyVacancyDetails().apply { resultCode = CLIENT_ERROR_RESULT_CODE }
        }

    }

    private suspend fun doCurrencyRequest(): Response {
        try {
            val response =
                headHunterApi.getCurrencies(BEARER_TOKEN)
            return CurrencyResponse(response.currency).apply { resultCode = CLIENT_SUCCESS_RESULT_CODE }
        } catch (e: IOException) {
            Log.e(NETWORK_ERROR, e.toString())
            return CurrencyResponse(null).apply { resultCode = CLIENT_ERROR_RESULT_CODE }
        }
    }

    private suspend fun doSearchRequest(options: Map<String, String>): Response {
        try {
            val response =
                headHunterApi.searchVacancies(BEARER_TOKEN, options)
            return SearchResponse(
                response.items,
                response.page,
                response.pages,
                response.found
            ).apply { resultCode = CLIENT_SUCCESS_RESULT_CODE }
        } catch (e: IOException) {
            Log.e(NETWORK_ERROR, e.toString())
            return SearchResponse(null, 0, 0, 0).apply { resultCode = CLIENT_ERROR_RESULT_CODE }
        }
    }

    override suspend fun getIndustries(): Result<List<IndustryResponse>> {
        if (!isConnected()) {
            return Result.failure(ConnectException())
        }
        val result = withContext(Dispatchers.IO) {
            try {
                val resultList = headHunterApi.getIndustries()
                Result.success(resultList)
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: SocketTimeoutException) {
                Result.failure(e)
            }
        }
        return result
    }

    override suspend fun getCountries(): Result<List<AreaDTO>> {
        if (!isConnected()) {
            return Result.failure(ConnectException())
        }
        return withContext(Dispatchers.IO) {
            try {
                val countries = headHunterApi.getCountries()
                Result.success(countries)
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: SocketTimeoutException) {
                Result.failure(e)
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            )
    }

    private fun createEmptyVacancyDetails(): VacancyDetailsResponse {
        return VacancyDetailsResponse(
            id = "",
            name = "",
            salary = null,
            employer = null,
            area = AreaDTO("", "", ""),
            experience = ExperienceDTO(""),
            description = "",
            keySkills = listOf(),
            contacts = null,
            alternateUrl = "",
        )
    }

    companion object {
        const val CLIENT_ERROR_RESULT_CODE = 400
        const val CLIENT_SUCCESS_RESULT_CODE = 200
        const val NO_INTERNET_RESULT_CODE = -1
        const val NETWORK_ERROR = "NETWORK ERROR"
        const val BEARER_TOKEN = "Bearer " + BuildConfig.HH_ACCESS_TOKEN
    }
}
