package ru.practicum.android.diploma.data.network


import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
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

class RetrofitNetworkClient(private val headHunterApi: HeadHunterApi) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
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
                        val response = headHunterApi.getVacancyDetails("Bearer " + BuildConfig.HH_ACCESS_TOKEN, dto.vacancyId)
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
                    } catch (e: HttpException) {
                        Log.e("NETWORK ERROR", e.toString())
                        VacancyDetailsResponse(
                            "", "", null, Employer(""), Area(""), Experience(""), "", "", "", "", emptyList(), Contacts("", "", ""), null
                        ).apply { resultCode = CLIENT_ERROR_RESULT_CODE }
                    } catch (e: IOException) {
                        Log.e("NETWORK ERROR", e.toString())
                        VacancyDetailsResponse(
                            "", "", null, Employer(""), Area(""), Experience(""), "", "", "", "", emptyList(), Contacts("", "", ""), null
                        ).apply { resultCode = CLIENT_ERROR_RESULT_CODE }
                    }
                }
            }

            else -> Response().apply { resultCode = CLIENT_ERROR_RESULT_CODE }
        }
    }

    suspend fun doRequestForDetails(vacancyId: String): Response {
        return doRequest(VacancyDetailsRequest(vacancyId))
    }

    companion object {
        const val CLIENT_ERROR_RESULT_CODE = 400
        const val CLIENT_SUCCESS_RESULT_CODE = 200
    }
}
