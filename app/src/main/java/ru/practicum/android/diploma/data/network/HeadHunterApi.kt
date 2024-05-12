package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.SearchResponse

interface HeadHunterApi {
    @Headers("HH-User-Agent: Application Name (name@example.com)")
    @GET("vacancies")
    suspend fun searchVacancies(
        @Header("Authorization: Bearer ") accessToken: String,
        @QueryMap options: Map<String, String>
    ): SearchResponse
}
