package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class SearchState {
    object Loading : SearchState()
    object Default : SearchState()
    object Empty : SearchState()
    object NoConnection : SearchState()
    data class Error(val message: String) : SearchState()
    data class Content(val vacancies: List<Vacancy>) : SearchState()
}
