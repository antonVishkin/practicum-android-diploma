package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Contacts
import ru.practicum.android.diploma.data.dto.CurrencyRequest
import ru.practicum.android.diploma.data.dto.CurrencyResponse
import ru.practicum.android.diploma.data.dto.Employer
import ru.practicum.android.diploma.data.dto.Experience
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.SearchRequest
import ru.practicum.android.diploma.data.dto.SearchResponse
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import java.io.IOException

class RetrofitNetworkClient(private val headHunterApi: HeadHunterApi, private val context: Context) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = NO_INTERNET_RESULT_CODE }
        }
        return when (dto) {
            is SearchRequest -> {
                withContext(Dispatchers.IO) {
                    try {
                        val response =
                            headHunterApi.searchVacancies("Bearer " + BuildConfig.HH_ACCESS_TOKEN, dto.options)
                        SearchResponse(
                            response.items,
                            response.page,
                            response.pages,
                            response.found
                        ).apply { resultCode = CLIENT_SUCCESS_RESULT_CODE }
                    } catch (e: IOException) {
                        Log.e("NETWORK ERROR", e.toString())
                        SearchResponse(null, 0, 0, 0).apply { resultCode = CLIENT_ERROR_RESULT_CODE }
                    }
                }
            }

            is CurrencyRequest -> {
                withContext(Dispatchers.IO) {
                    try {
                        val response =
                            headHunterApi.getCurrencies("Bearer " + BuildConfig.HH_ACCESS_TOKEN)
                        CurrencyResponse(response.currency).apply { resultCode = CLIENT_SUCCESS_RESULT_CODE }
                    } catch (e: IOException) {
                        Log.e("NETWORK ERROR", e.toString())
                        CurrencyResponse(null).apply { resultCode = CLIENT_ERROR_RESULT_CODE }
                    }
                }
            }

            is VacancyDetailsRequest -> {
                withContext(Dispatchers.IO) {
                    try {
                        val response =
                            headHunterApi.getVacancyDetails("Bearer " + BuildConfig.HH_ACCESS_TOKEN, dto.vacancyId)
                        VacancyDetailsResponse(
                            response.id,
                            response.name,
                            response.logoUrl,
                            response.employer,
                            response.area,
                            response.experience,
                            response.description,
                            response.responsibilities,
                            response.requirements,
                            response.conditions,
                            response.keySkills,
                            response.contacts,
                            response.comments
                        ).apply { resultCode = CLIENT_SUCCESS_RESULT_CODE }
                    } catch (e: IOException) {
                        Log.e("NETWORK ERROR", e.toString())
                        VacancyDetailsResponse(
                            "",
                            "",
                            null,
                            Employer(""),
                            Area(""),
                            Experience(""),
                            "",
                            "",
                            "",
                            "",
                            emptyList(),
                            Contacts(
                                "",
                                "",
                                ""
                            ),
                            null
                        ).apply { resultCode = CLIENT_ERROR_RESULT_CODE }
                    }
                }
            }

            else -> Response().apply { resultCode = CLIENT_ERROR_RESULT_CODE }
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

    companion object {
        const val CLIENT_ERROR_RESULT_CODE = 400
        const val CLIENT_SUCCESS_RESULT_CODE = 200
        const val NO_INTERNET_RESULT_CODE = -1

    }
}
