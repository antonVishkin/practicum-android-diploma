package ru.practicum.android.diploma.ui.filtration.industry

import androidx.annotation.StringRes
import ru.practicum.android.diploma.domain.models.Industry

sealed interface IndustryState {
    object Loading : IndustryState
    object NotFound : IndustryState
    object Selected : IndustryState
    data class Content(val industries: List<Industry>) : IndustryState
    data class ServerError(@StringRes val message: Int) : IndustryState
    data class NoConnection(@StringRes val message: Int) : IndustryState
}
