package ru.practicum.android.diploma.ui.vacancies

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface VacancyDetailsState {
    object Error : VacancyDetailsState
    object Loading : VacancyDetailsState
    object NotInDb : VacancyDetailsState
    data class Content(val vacancy: Vacancy, val currencySymbol: String, val isFavorite: Boolean) :
        VacancyDetailsState
    data class NoConnection(val vacancy: Vacancy) : VacancyDetailsState

}
