package ru.practicum.android.diploma.domain.api.details

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.vacancies.VacancyDetailStatus

interface VacancyDetailsInteractor {
    fun getVacancyDetails(vacancyId: String): Flow<VacancyDetailStatus<Vacancy>>
}
