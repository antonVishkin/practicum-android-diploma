package ru.practicum.android.diploma.ui.filtration

import ru.practicum.android.diploma.domain.models.Filtration

sealed interface FiltrationState {
    object Empty : FiltrationState
    data class Content(val filtration: Filtration) : FiltrationState
}
