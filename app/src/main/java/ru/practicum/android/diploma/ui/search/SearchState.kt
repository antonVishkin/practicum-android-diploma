package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.VacancyPage

sealed class SearchState {
    object Loading : SearchState()
    object NewPageLoading : SearchState()
    object Default : SearchState()
    object Empty : SearchState()
    object NoConnection : SearchState()
    data class Error(val message: String) : SearchState()
    data class Content(val vacancyPage: VacancyPage, val currencyDictionary: Map<String, Currency>) : SearchState()
}
