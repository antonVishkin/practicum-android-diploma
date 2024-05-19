package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.CurrencyResponse
import ru.practicum.android.diploma.data.dto.SearchResponse

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
}
