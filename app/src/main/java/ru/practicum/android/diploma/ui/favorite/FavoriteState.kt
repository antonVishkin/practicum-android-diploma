package ru.practicum.android.diploma.ui.favorite

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage

sealed interface FavoriteState {
    object Empty : FavoriteState
    object Error : FavoriteState
    object Loading : FavoriteState
    object LoadingNewPage : FavoriteState
    data class Content(val favorite: List<Vacancy>) : FavoriteState
}
