package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyDTO

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
