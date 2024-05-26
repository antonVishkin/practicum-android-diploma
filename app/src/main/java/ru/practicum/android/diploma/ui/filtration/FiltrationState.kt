package ru.practicum.android.diploma.ui.filtration

import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filtration
import ru.practicum.android.diploma.domain.models.Industry

sealed interface FiltrationState {
    object Empty:FiltrationState
    data class Content(val filtration: Filtration):FiltrationState
}
