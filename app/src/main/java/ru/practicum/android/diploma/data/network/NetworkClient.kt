package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.AreaDTO
import ru.practicum.android.diploma.data.dto.IndustryResponse
import ru.practicum.android.diploma.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
    suspend fun getIndustries(): Result<List<IndustryResponse>>?
    suspend fun getCountries(): Result<List<AreaDTO>>?
}

