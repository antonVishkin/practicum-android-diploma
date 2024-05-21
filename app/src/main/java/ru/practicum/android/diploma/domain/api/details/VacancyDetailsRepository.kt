package ru.practicum.android.diploma.domain.api.details

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyPage

interface VacancyDetailsRepository {
    fun getVacancyDetails(vacancyId: String): Flow<Result<VacancyPage>>
}
