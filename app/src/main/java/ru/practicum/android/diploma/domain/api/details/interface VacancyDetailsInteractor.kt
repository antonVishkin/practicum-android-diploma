package ru.practicum.android.diploma.domain.api.details

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface VacancyDetailsInteractor {
    fun getVacancyDetails(vacancyId: String): Flow<Result<VacancyDetails>>
}
