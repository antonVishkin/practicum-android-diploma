package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.VacancyDTO

interface NetworkClient {
    suspend fun doRequest(dto: Any): Result<List<VacancyDTO>>
}
