package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.IndustryForResponceWithList
import ru.practicum.android.diploma.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
    suspend fun getIndustries(): Result<List<IndustryForResponceWithList>>?
}
