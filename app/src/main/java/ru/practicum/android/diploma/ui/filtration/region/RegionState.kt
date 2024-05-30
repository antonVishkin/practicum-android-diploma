package ru.practicum.android.diploma.ui.filtration.region

import androidx.annotation.StringRes
import ru.practicum.android.diploma.domain.models.Region

sealed interface RegionState {
    object Loading : RegionState
    object NotFound : RegionState
    object Selected : RegionState
    data class Content(val regions: List<Region>) : RegionState
    data class ServerError(@StringRes val message: Int) : RegionState
    data class NoConnection(@StringRes val message: Int) : RegionState
}
