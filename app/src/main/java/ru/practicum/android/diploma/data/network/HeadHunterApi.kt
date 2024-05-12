package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.Response

interface HeadHunterApi{
    @GET("vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>):Response
}
