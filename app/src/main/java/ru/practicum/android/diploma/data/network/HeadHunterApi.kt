package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.CurrencyResponse
import ru.practicum.android.diploma.data.dto.SearchResponse
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse

interface HeadHunterApi {
    @Headers("HH-User-Agent: praktikum HH API v.9.3 (punkant@gmail.com)")
    @GET("vacancies")
    suspend fun searchVacancies(
        @Header("Authorization") accessToken: String,
        @QueryMap options: Map<String, String>
    ): SearchResponse

    @Headers("HH-User-Agent: praktikum HH API v.9.3 (punkant@gmail.com)")
    @GET("dictionaries")
    suspend fun getCurrencies(
        @Header("Authorization") accessToken: String
    ): CurrencyResponse

    @Headers("HH-User-Agent: praktikum HH API v.9.3 (punkant@gmail.com)")
    @GET("vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(
        @Header("Authorization") accessToken: String,
        @Query("vacancy_id") vacancy_id: String
    ): VacancyDetailsResponse
}
