package ru.practicum.android.diploma.ui.vacancies

import ru.practicum.android.diploma.domain.models.VacancyDetails

sealed interface VacancyState {
    object Loading : VacancyState
    object Error : VacancyState
    data class Content(val vacancyDetails: VacancyDetails, val currencySymbol: String, val isFavorite:Boolean) : VacancyState
}
