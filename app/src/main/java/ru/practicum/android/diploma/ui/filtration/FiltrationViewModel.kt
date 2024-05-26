package ru.practicum.android.diploma.ui.filtration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filtration.FiltrationInteractor
import ru.practicum.android.diploma.domain.models.Filtration
import ru.practicum.android.diploma.ui.filtration.FiltrationState.Content

class FiltrationViewModel(private val filtrationInteractor: FiltrationInteractor) : ViewModel() {
    private val _state = MutableLiveData<FiltrationState>()
    val state: LiveData<FiltrationState> get() = _state

    init {
        _state.value = FiltrationState.Empty
        getFiltrationFromPrefs()
    }


    fun saveStateToPrefs() {
        val filtration = (state.value as Content).filtration
        viewModelScope.launch {
            filtrationInteractor.saveFiltration(filtration)
        }
    }

    private fun getFiltrationFromPrefs() {
        viewModelScope.launch {
            val filtration = filtrationInteractor.getFiltration()
            if (filtration != null)
                renderState(
                    Content(
                        filtration
                    )
                )
            else {
                renderState(
                    FiltrationState.Empty
                )
            }
        }
    }

    fun setCheckbox(onlyWithSalary: Boolean) {
        var filtration: Filtration = if (state.value is Content) {
            (state.value as Content).filtration
        } else {
            Filtration(null, null, null, false)
        }
        renderState(
            Content(Filtration(filtration.area,filtration.industry,filtration.salary,onlyWithSalary))
        )
    }

    private fun renderState(state: FiltrationState) {
        _state.value = state
    }
}
